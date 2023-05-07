package com.example.demo.service;

import com.example.demo.entity.VideoComment;
import com.example.demo.entity.VideoReply;
import com.example.demo.mapper.VideoReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReplyService {
    @Resource
    VideoReplyMapper videoReplyMapper;
    public boolean uploadVideoReply(int uid,int cid,String reply)
    {

        return videoReplyMapper.addVideoReply(uid, cid, reply);
    }

    public VideoReply[] getReplies(int cid)
    {
        return videoReplyMapper.findVideoReply(cid);
    }
    public boolean deleteVideoReply(int rid)
    {
        return videoReplyMapper.deleteVideoReply(rid);
    }
}
