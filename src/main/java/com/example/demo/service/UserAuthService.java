package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.mapper.AuthMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

@Service
public class UserAuthService implements UserDetailsService {
    @Resource
    AuthMapper mapper;

    @Resource
    UserInformationService userInformationService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=mapper.findAccountByAccountOrMail(username);
        if(account==null)
            throw new UsernameNotFoundException("不存在的用户");
        return User.withUsername(account.getAccount()).password(account.getPassword()).roles(account.getRole()).build();
    }
    public Account getAccountByUid(String uid)
    {
        return mapper.findAccountByUid(uid);
    }
    public boolean registerUser(String account,String password,String mail)
    {

        try {
             mapper.addAccount(account,new BCryptPasswordEncoder().encode(password),mail);
             return true;
        }catch (Exception exception)
        {
            return false;
        }

    }
    public boolean changeMailByUid(String mail,String uid)
    {


         return   mapper.editMailByUId(mail,uid);


    }

    public boolean registerUser(String account,String password,String mail,String role)
    {

        try {
            return mapper.addAccountIncludeRole(account,new BCryptPasswordEncoder().encode(password),mail,role);

        }catch (Exception exception)
        {
            return false;
        }

    }
    public Account getCreateTimeByUid(String uid)
    {
        return mapper.findCreateTimeByUid(uid);
    }

    public boolean editAccountByUid(String account,String password,String mail,String role,String uid)
    {
        return mapper.editAccountByUId(account,new BCryptPasswordEncoder().encode(password),mail,role,uid);
    }

    public boolean editAccountByUid(String account,String password,String uid)
    {
        return mapper.editAccountByUidUserVersion(account,new BCryptPasswordEncoder().encode(password),uid);
    }
    public boolean editRole(String uid,String role)
    {
       return mapper.editRoleByUId(uid,role);
    }

    public boolean deleteAccountByUid(String uid)
    {
        return mapper.dropAccountByUid(uid);
    }
}
