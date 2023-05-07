package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.entity.SimpleUserInformation;
import com.example.demo.entity.UserInformation;
import com.example.demo.service.UploadFileService;
import com.example.demo.service.UserInformationService;
import com.example.demo.utils.CookieUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Principal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Random;

@RequestMapping("/user/api")
@RestController
public class UserApiController {


    @Resource
    UserInformationService userInformationService;
    @RequestMapping("/simpleUserInformation")
    public RestBean getSimpleUserInformation(Principal principal, HttpServletResponse response)
    {

        SimpleUserInformation user=userInformationService.loadSimpleUserInformationByAccount(principal.getName());
        Cookie cookie=new Cookie("uid",""+user.getUid());
        cookie.setPath("/");
        response.addCookie(cookie);
        return new RestBean<SimpleUserInformation>(200,"your information",user);
    }
    @RequestMapping("/userInformation")
    public RestBean getUserInformation(HttpServletRequest request)
    {
        if (request.getParameter("uid")!=null&&!request.getParameter("uid").equals(""))
        {
            String uid=request.getParameter("uid");
            return new RestBean<UserInformation>(200,"your information",userInformationService.loadUserInformationByUid(Integer.valueOf(uid)));
        }else
        {
            String uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
            if(uid!=null)
                return new RestBean<UserInformation>(200,"your information",userInformationService.loadUserInformationByUid(Integer.valueOf(uid)));
            return new RestBean<>(302,"no uid");
        }




    }

    @PostMapping("/submitUserInformation")
    public RestBean getUserInformation(@RequestParam("name")String name, @RequestParam("birthday")Date birthday,@RequestParam("phone")String phone,@RequestParam("address")String address,@RequestParam("profile")String profile,@RequestParam(value = "uid",required = false,defaultValue = "")String uid,HttpServletRequest request)
    {
        if(uid.equals(""))
            uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
            userInformationService.changeUser(new UserInformation(Integer.parseInt(uid),name,birthday,phone,address,profile));

        return new RestBean<>(302,"no uid");
    }
    @RequestMapping("/upLoadPortrait")
    public RestBean upLoadPortrait(@RequestParam("croppedImage") String file,@RequestParam(value = "uid",required = false,defaultValue = "")String uid,HttpServletRequest request)  {

        if(uid.equals(""))
            uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null) {
            try {
                userInformationService.changePortrait(file,Integer.parseInt(uid));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        return new RestBean(200,"ok");
//        if(fileService.saveFile(file))
//            return new RestBean(200,"ok");
//        else
//            return new RestBean(302,"no");
    }
}
