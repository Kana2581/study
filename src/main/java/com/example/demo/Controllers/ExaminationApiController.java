package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.mapper.ExaminationMapper;
import com.example.demo.service.ExaminationService;
import com.example.demo.utils.CookieUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/examination/api")
@RestController
public class ExaminationApiController {

    @Resource
    ExaminationService examinationService;

    @PostMapping("/up-examination")
    public RestBean upExamination(@RequestParam("title")String title, @RequestParam("startTime")String startTime, @RequestParam("deadTime")String deadTime,@RequestParam("pid")String pid,HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null) {
            return new RestBean(200, "hah",examinationService.addExamination(title,startTime,deadTime,pid));
        }else
            return new RestBean(302,"no cookie",false);

    }
    @PostMapping("/edit-examination")
    public RestBean editExamination(@RequestParam("title")String title, @RequestParam("startTime")String startTime, @RequestParam("deadTime")String deadTime,@RequestParam("eid")String eid,HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null) {
            return new RestBean(200, "hah",examinationService.changeExamination(title,startTime,deadTime,eid));
        }else
            return new RestBean(302,"no cookie",false);

    }
    @GetMapping("/examination-list")
    public RestBean myExamination(@RequestParam("pid")String pid,HttpServletRequest request)
    {
        return new RestBean(200, "hah",examinationService.getExaminationByPid(pid));
    }
    @GetMapping("/examination-information")
    public RestBean Examination(@RequestParam("eid")String eid)
    {
        return new RestBean(200, "hah",examinationService.getExaminationByEid(eid));
    }
}
