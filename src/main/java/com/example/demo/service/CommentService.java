package com.example.demo.service;

import com.example.demo.entity.VideoComment;
import com.example.demo.mapper.ProjectCommentMapper;
import com.example.demo.mapper.VideoCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService {
    @Resource
    VideoCommentMapper videoCommentMapper;
    @Resource
    ProjectCommentMapper projectCommentMapper;
    public void uploadVideoComment(int uid,int vid,String Comment)
    {

        videoCommentMapper.addVideoComment(uid, vid, Comment);
    }
    public void uploadProjectComment(int uid,int pid,String Comment)
    {

        projectCommentMapper.addProjectComment(uid, pid, Comment);
    }
    public VideoComment[] getComments(int vid)
    {
        return videoCommentMapper.findVideoComment(vid);
    }
    public VideoComment[] getProjectComments(int pid)
    {
        return projectCommentMapper.findProjectComment(pid);
    }
    public boolean deleteVideoComments(int cid)
    {
        return videoCommentMapper.deleteVideoComment(cid);
    }
}
