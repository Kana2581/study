package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.mapper.ProgressMapper;
import com.example.demo.service.ProgressService;
import com.example.demo.utils.CookieUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/progress/api")
@RestController
public class ProgressController {
    @Resource
    ProgressService progressService;
    @PostMapping("/up-vedio-progress")
    public void editProject(@RequestParam("vid") String vid,@RequestParam("pid") String pid,@RequestParam(value = "uid",required = false,defaultValue = "") String uid, @RequestParam("studyTime") int studyTime,HttpServletRequest request)
    {
        if(uid.equals(""))
            uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        progressService.changeVideoProgress(vid,uid,studyTime);
        progressService.changeCourseProgress(pid,uid);

    }
}
