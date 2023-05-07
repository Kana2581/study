package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.entity.SimpleUserInformation;
import com.example.demo.service.TeacherCodeService;
import com.example.demo.service.UserAuthService;
import com.example.demo.utils.CookieUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequestMapping("/TeacherCode/api")
@RestController
public class TeacherCodeController {
    @Resource
    TeacherCodeService teacherCodeService;
    @Resource
    UserAuthService userAuthService;
    @RequestMapping("/use-teacher-code")
    public RestBean useCode(@RequestParam("code") String code, HttpServletRequest request)
    {
        if(teacherCodeService.deleteByCode(code))
        {
            String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
            return new RestBean(200,"ok",userAuthService.editRole(uid,"teacher"));

        }else
            return new RestBean(302,"error");

    }
}
