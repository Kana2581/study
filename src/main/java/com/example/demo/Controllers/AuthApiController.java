package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.entity.UserInformation;
import com.example.demo.service.MailVerityService;
import com.example.demo.service.UserAuthService;
import com.example.demo.utils.CookieUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.web.servlet.CsrfDsl;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;

@RequestMapping("/auth/api")
@RestController
public class AuthApiController {
    @Resource
    UserAuthService authService;
    @Resource
    MailVerityService service;
    @Resource
    StringRedisTemplate template;
    @RequestMapping("mail_code")
    public RestBean verify(@RequestParam("mail") String mail)
    {
        service.verity(mail);
        return new RestBean(200,"ok");
    }



    @PostMapping("registerAccount")
    public RestBean registerAccount(@RequestParam("username")String username, @RequestParam("email")String email, @RequestParam("code")String code, @RequestParam("password")String password)
    {


        if(!code.equals(template.opsForValue().get("mail"+email)))
            return new RestBean(302,"email error");
        if(authService.registerUser(username,password,email))
            return new RestBean(200,"ok");
        else
            return new RestBean(302,"error");

    }

    @PostMapping("change-email")
    public RestBean changeEmail(@RequestParam("email")String email, @RequestParam("code")String code,HttpServletRequest request)
    {

        String uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(!code.equals(template.opsForValue().get("mail"+email)))
            return new RestBean(302,"email error");
        if(authService.changeMailByUid(email,uid))
            return new RestBean(200,"ok");
        else
            return new RestBean(302,"error");

    }

    @PostMapping("/add-account")
    public RestBean addAccount(@RequestParam("name")String name,@RequestParam("password")String password, @RequestParam("email")String email, @RequestParam("role")String role )
    {


        if(authService.registerUser(name,password,email,role))
            return new RestBean(200,"ok");
        else
            return new RestBean(302,"error");

    }

    @PostMapping("/edit-account")
    public RestBean editAccount(@RequestParam("name")String name,@RequestParam("password")String password, @RequestParam("email")String email, @RequestParam("role")String role,@RequestParam("uid")String uid )
    {


        if(authService.editAccountByUid(name,password,email,role,uid))
            return new RestBean(200,"ok");
        else
            return new RestBean(302,"error");

    }
    @PostMapping("/edit-account-user-version")
    public RestBean editAccount(@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("code")String code,HttpServletRequest request )
    {
        if(request.getSession().getAttribute("verifyCode").equals(code))
        {
            String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
            if(authService.editAccountByUid(name,password,uid))
                return new RestBean(200,"ok");
            else
                return new RestBean(302,"error");

        }else return new RestBean(302,"error");

    }
    @GetMapping("/delete-account")
    public void deleteAccount(@RequestParam("uid")String uid,HttpServletResponse response)
    {
        authService.deleteAccountByUid(uid);
        try {
            response.sendRedirect("/account-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
    @RequestMapping("/account-information")
    public RestBean getDate(HttpServletRequest request) {
        if (request.getParameter("uid")!=null)
        {
            String uid=request.getParameter("uid");
             return new RestBean(200, "hah",authService.getCreateTimeByUid(uid));
        }else
        {
            String uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
            if(uid!=null)
                return new RestBean(200, "hah",authService.getCreateTimeByUid(uid));
            return new RestBean<>(302,"no uid");
        }

    }

    @RequestMapping("/account-information-by-uid")
    public RestBean getData(HttpServletRequest request) {
        if (request.getParameter("uid")!=null)
        {
            String uid=request.getParameter("uid");
            return new RestBean(200, "hah",authService.getAccountByUid(uid));
        }else
        {
            String uid=CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
            if(uid!=null)
                return new RestBean(200, "hah",authService.getAccountByUid(uid));
            return new RestBean<>(302,"no uid");
        }

    }


    @Resource
    private DefaultKaptcha captchaProducer;

    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
