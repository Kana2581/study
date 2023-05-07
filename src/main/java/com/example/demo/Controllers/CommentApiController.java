package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.service.CommentService;
import com.example.demo.utils.CookieUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/comment/api")
@RestController
public class CommentApiController {
    @Resource
    CommentService commentService;

    @PostMapping("up-video-comment")
    public RestBean videoComment(@RequestParam("vid")int vid,@RequestParam("comment")String comment, HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        commentService.uploadVideoComment(Integer.parseInt(uid),vid,comment);
        return new RestBean(200,"ok");
    }
    @PostMapping("up-project-comment")
    public RestBean projectComment(@RequestParam("pid")int pid,@RequestParam("comment")String comment, HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        commentService.uploadProjectComment(Integer.parseInt(uid),pid,comment);
        return new RestBean(200,"ok");
    }
    @GetMapping("/comments")
    public RestBean LoadVideoInformation(@RequestParam(value = "vid") int vid)
    {
        return new RestBean(200,"hah",commentService.getComments(vid));
    }
    @GetMapping("/projectComments")
    public RestBean LoadProjectCommentsInformation(@RequestParam(value = "pid") int pid)
    {
        return new RestBean(200,"hah",commentService.getProjectComments(pid));
    }
    @GetMapping("/delete-video-comment")
    public RestBean deleteVideoComment(@RequestParam(value = "cid") int cid)
    {
        return new RestBean(200,"hah",commentService.deleteVideoComments(cid));
    }

}
