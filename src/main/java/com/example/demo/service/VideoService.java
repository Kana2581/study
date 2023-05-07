package com.example.demo.service;

import com.example.demo.entity.SimpleVideoInformation;
import com.example.demo.entity.UserInformation;
import com.example.demo.entity.Video;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.SQLException;
@EnableTransactionManagement
@Service
public class VideoService {
    @Resource
    UploadFileService uploadFileService;
    @Resource
    VideoMapper videoMapper;
    public void addUserVideo(int id, MultipartFile video,String name,float duration)
    {

        uploadFileService.addVideo(video,name);
        videoMapper.addVideo(id,name,duration);
    }
    @Transactional
    public void addVideoInformationByVideoName(String videoName,String title,String message) {


        String fileName= RandomNameUtil.createRandomName() +".jpeg";

        videoMapper.editVideoByVideo(title,message,videoName);
    }
    public Video[] loadVideosByPid(int pid)
    {
        return videoMapper.findVideosByPid(pid);
    }
    public SimpleVideoInformation[] getSimpleVideoByPidAndUid(int pid, int uid)
    {
        return videoMapper.findSimpleVideoByPidAndUid(pid,uid);
    }
    public Integer getFirstVidByPid(int pid)
    {
        return videoMapper.findFirstVidByPid(pid);
    }
    public Boolean deleteVideoByVid(int vid)
    {
        FileUtil.deleteFile(FileUtil.videosPath+videoMapper.findVideoNameByVid(vid));
        return videoMapper.dropVideoByVid(vid);
    }
}
