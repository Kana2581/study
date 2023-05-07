package com.example.demo.service;

import com.example.demo.entity.UserInformation;
import com.example.demo.mapper.RelationshipMapper;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RelationshipService {
    @Resource
    UserInformationService userInformationService;
    @Resource
    RelationshipMapper mapper;
    @Resource
    StringRedisTemplate template;
    public String createUserCode(int uid)
    {
        String code=RandomNameUtil.generateRandomCode(16);
        template.opsForValue().set("user_uid_"+uid,code,5, TimeUnit.MINUTES);
        template.opsForValue().set("user_code_"+code,""+uid,5, TimeUnit.MINUTES);
        return code;
    }
    public String getUserCode(int uid)
    {
        return template.opsForValue().get("user_uid_"+uid);
    }
    public UserInformation createRelationShipByUserCode(String code,String studentUid)
    {
        String teacherUid=template.opsForValue().get("user_code_"+code);
        if(teacherUid!=null&&!teacherUid.equals(studentUid))
        {
            if(mapper.loadRelationshipByUid(studentUid,teacherUid))
                return  userInformationService.loadUserInformationByUid(Integer.parseInt(teacherUid));
        }
        return null;

    }
    public UserInformation[] getStudent(String uid)
    {
        return mapper.findStudentByTeacherUid(uid);
    }
    public boolean deleteStudentByUid(String studentUid,String teacherUid)
    {
        return mapper.dropRelationshipByUid(studentUid,teacherUid);
    }
}
