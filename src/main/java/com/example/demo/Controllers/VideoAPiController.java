package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.entity.Video;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UploadFileService;
import com.example.demo.service.VideoService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;


@RequestMapping("/video/api")
@RestController
public class VideoAPiController {
    @Resource
    VideoService videoService;
    @Resource
    ProjectService projectService;
    @PostMapping("/upVideo")
    public RestBean upLoadVideo(@RequestParam("video") MultipartFile video,@RequestParam("pid") int pid,@RequestParam("duration")float duration, HttpServletRequest request) {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(projectService.getUidByPid(pid)==Integer.parseInt(uid))
        {
            String videoName= RandomNameUtil.createRandomName()+".mp4";
            videoService.addUserVideo(pid,video,videoName,duration);
            return new RestBean(233,"hah",videoName);
        }else
            return new RestBean(302,"nmsl");
    }
    @PostMapping("/up-video-information")
    public RestBean upLoadVideoInformation(@RequestParam("video-name") String videoName,@RequestParam("title") String title,@RequestParam("message") String message)
    {
        videoService.addVideoInformationByVideoName(videoName,title,message);
        return new RestBean(200,"hah");
    }
    @GetMapping("/video-page")
    public RestBean LoadVideoInformation(@RequestParam(value = "pid") int pid)
    {
        return new RestBean(200,"hah",videoService.loadVideosByPid(pid));
    }
    @GetMapping("/my-video-progress")
    public RestBean formation(@RequestParam(value = "pid") int pid,@RequestParam(value = "uid",required = false,defaultValue = "") String uid,HttpServletRequest request)
    {
        if (uid.equals(""))
        {
            uid=CookieUtil.getCookieFromCookies(request.getCookies(), "uid");
        }
        return new RestBean(200,"hah",videoService.getSimpleVideoByPidAndUid(pid,Integer.parseInt(uid)));
    }
    @GetMapping("/first-vid")
    public Integer LoadFirstVideo(@RequestParam(value = "pid") int pid)
    {
        return videoService.getFirstVidByPid(pid);
    }

    @GetMapping("/delete-video")
    public void deleteVideo(@RequestParam(value = "vid") int vid,@RequestParam(value = "pid") int pid, HttpServletResponse response)
    {
        try {
            response.sendRedirect("/project-modification?pid="+pid+"&delete="+videoService.deleteVideoByVid(vid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
