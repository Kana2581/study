package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProgressMapper {
    @Update("INSERT INTO video_progress(vid,uid,study_time) VALUES (#{vid},#{uid},#{studyTime}) ON DUPLICATE KEY UPDATE video_progress.`study_time`=VALUES(study_time);")
    public boolean editVideoProgress(@Param("vid") String vid, @Param("uid")String uid, @Param("studyTime") int studyTime);
    @Update("INSERT INTO course_history(pid,uid) VALUES (#{pid},#{uid}) ON DUPLICATE KEY UPDATE course_history.`last_time`=now()")
    public boolean editCourseHistory(@Param("pid") String pid, @Param("uid")String uid);
}
