package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.mapper.RelationshipMapper;
import com.example.demo.service.RelationshipService;
import com.example.demo.service.UserInformationService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.RandomNameUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/relationship/api")
@RestController
public class RelationshipController {


    @Resource
    RelationshipService relationshipService;
    @GetMapping("/generate-user-code")
    public RestBean getMyProjects(HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            return new RestBean(200,"hah",relationshipService.createUserCode(Integer.parseInt(uid)));

        }else
            return new RestBean(302,"no cookie");


    }
    @GetMapping("/user-code")
    public RestBean getProjects(HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            return new RestBean(200,"hah",relationshipService.getUserCode(Integer.parseInt(uid)));

        }else
            return new RestBean(302,"no cookie");

    }
    @GetMapping("/add-teacher-by-user-code")
    public RestBean getPro(@RequestParam("code")String code, HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            return new RestBean(200,"hah",relationshipService.createRelationShipByUserCode(code,uid));
        }else
            return new RestBean(302,"no cookie");

    }
    @GetMapping("/student-list")
    public RestBean getStudents(HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            return new RestBean(200,"hah",relationshipService.getStudent(uid));
        }else
            return new RestBean(302,"no cookie");
    }
    @GetMapping("/delete-relationship")
    public void deleteStudents(@Param("student")String student, HttpServletRequest request, HttpServletResponse response)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");

        try {
            response.sendRedirect("/student-management?delete="+relationshipService.deleteStudentByUid(student,uid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
