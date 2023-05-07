package com.example.demo.service;

import com.example.demo.mapper.ProgressMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service
public class ProgressService {
    @Resource
    ProgressMapper progressMapper;
    public void changeVideoProgress(String vid,String uid, int studyTime)
    {
        try {
            progressMapper.editVideoProgress(vid, uid, studyTime);
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
    public void changeCourseProgress(String pid,String uid)
    {

            progressMapper.editCourseHistory(pid, uid);


    }
}
