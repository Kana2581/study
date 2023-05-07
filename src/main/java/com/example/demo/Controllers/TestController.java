package com.example.demo.Controllers;

import com.example.demo.entity.Project;
import com.example.demo.entity.RestBean;
import com.example.demo.service.ProjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller

public class TestController {
    @Resource
    ProjectService projectService;
    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       Cookie[] c=request.getCookies();
        System.out.println(c.length);
       for(int i=0;i<c.length;i++) {
           c[i].setMaxAge(0);
           response.addCookie(c[i]);
       }

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String index(Authentication authentication)
    {
        return "index";
    }
    @RequestMapping("/form")
    public String form()
    {
        return "form";
    }
    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }
    @RequestMapping("/up-video")
    public String upVideo(@RequestParam("pid")int pid)
    {
        return "up-video";
    }
    @RequestMapping("/up-resource")
    public String upResource(@RequestParam("pid")int pid)
    {
        return "up-resource";
    }
    @RequestMapping("video")
    public String video()
    {
        return "video";
    }


    @GetMapping("/register")
    public String register()
    {
        return "register";
    }
    @RequestMapping("/project")
    public String project(){return "project";}
    @RequestMapping("/course")
    public String course(@RequestParam("pid")int pid)
    {
        return "course";
    }
    @RequestMapping("/watch")
    public String watch(@RequestParam("pid")int pid,@RequestParam("v")String v)
    {
        return "watch";
    }
    @RequestMapping("/course-management")
    public String courseManagement()
    {
        return "course-management";
    }
    @RequestMapping("/project-modification")
    public String courseModification()
    {
        return "project-modification";
    }
    @RequestMapping("/space")
    public String userProfile()
    {
        return "profile";
    }
    @RequestMapping("/student-management")
    public String userCode()
    {
        return "student-management";
    }

    @RequestMapping("/add-teacher")
    public String Code()
    {
        return "/add-teacher";
    }
    @RequestMapping("/up-examination")
    public String upExamination(@RequestParam("pid") int pid)
    {
        return "/up-examination";
    }
    @RequestMapping("/examination-question")
    public String Examination(@RequestParam("eid") int eid)
    {
        return "/examination-question";
    }

    @RequestMapping("/question-management")
    public String ExaminationQuestion(@RequestParam("eid") int eid)
    {
        return "/question-management";
    }

    @RequestMapping("/account-form")
    public String accountForm()
    {
        return "/account-form";
    }

    @RequestMapping("/edit-examination")
    public String examinationForm(@RequestParam("eid") int eid)
    {
        return "/edit-examination";
    }
    @RequestMapping("/account-list")
    public String examinationForm()
    {
        return "/account-list";
    }

    @RequestMapping("/user-list")
    public String userForm()
    {
        return "/user-list";
    }
    @RequestMapping("/edit-account")
    public String editAccount(@RequestParam("uid") int uid)
    {
        return "/edit-account";
    }

    @RequestMapping("/edit-account-user-version")
    public String editAccountUserVersion(@RequestParam( required = false,value = "uid") String uid)
    {
        return "/edit-account-user-version";
    }

    @RequestMapping("/become-teacher")
    public String becomeTeacher()
    {
        return "/become-teacher";
    }

    @RequestMapping("/course-history")
    public String history()
    {
        return "/course-history";
    }



    @RequestMapping("/video-progress")
    public String videoHistory()
    {
        return "/video-progress";
    }

    @RequestMapping("/select-examination")
    public String selectExamination()
    {
        return "/select-examination";
    }

    @RequestMapping("/examination")
    public String Examination()
    {
        return "/examination";
    }

}
