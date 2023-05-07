package com.example.demo.service;

import com.example.demo.entity.SimpleUserInformation;
import com.example.demo.entity.UserInformation;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.Base64ToFile;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;
import java.sql.SQLException;
import java.util.Random;

@EnableTransactionManagement
@Service
public class UserInformationService {
    @Resource
    UploadFileService fileService;
    @Resource
    UserMapper userMapper;
    public SimpleUserInformation loadSimpleUserInformationByAccount(String account)
    {
        SimpleUserInformation simpleUserInformation=userMapper.findSimpleUserInformationByAccount(account);
        if(simpleUserInformation==null)
            throw new UsernameNotFoundException("不存在的用户");
        return simpleUserInformation;

    }
    public UserInformation loadUserInformationByUid(int uid)
    {
        UserInformation userInformation=userMapper.findUserInformationByUid(uid);
        if(userInformation==null)
            throw new UsernameNotFoundException("不存在的用户");
        return userInformation;

    }
    @Transactional
    public void changePortrait(String base64Str,int uid) throws SQLException {
        UserInformation userInformation=loadUserInformationByUid(uid);
        if(userInformation!=null)
            FileUtil.deleteFile(FileUtil.portraitsPath+userInformation.getPortrait());

        String fileName= RandomNameUtil.createRandomName() +".jpeg";
        fileService.addImage(base64Str,FileUtil.portraitsPath+fileName);
        userMapper.editPortrait(uid,fileName);
    }
    public void changeUser(UserInformation userInformation)  {
        userMapper.editUserInformationExcludePortrait(userInformation);
    }

}
