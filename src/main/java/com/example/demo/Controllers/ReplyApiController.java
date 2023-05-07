package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.service.ReplyService;
import com.example.demo.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RequestMapping("/reply/api")
@RestController
public class ReplyApiController {
    @Resource
    ReplyService replyService;
    @PostMapping("up-video-reply")
    public RestBean videoComment(@RequestParam("cid")int cid, @RequestParam("reply")String reply, HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");

        return new RestBean(200,"ok",replyService.uploadVideoReply(Integer.parseInt(uid),cid,reply));
    }
    @GetMapping("video-replies-by-cid")
    public RestBean videoComment(@RequestParam("cid")int cid)
    {
        return new RestBean(200,"ok",replyService.getReplies(cid));
    }

    @GetMapping("/delete-video-reply")
    public RestBean deleteVideoComment(@RequestParam(value = "rid") int rid)
    {
        return new RestBean(200,"hah",replyService.deleteVideoReply(rid));
    }

}
