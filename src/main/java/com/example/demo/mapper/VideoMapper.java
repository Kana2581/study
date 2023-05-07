package com.example.demo.mapper;

import com.example.demo.entity.SimpleVideoInformation;
import com.example.demo.entity.UserInformation;
import com.example.demo.entity.Video;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VideoMapper {
    @Insert("insert into video(pid,video,duration) value(#{pid},#{video},#{duration})")
    public Boolean addVideo(@Param("pid") int pid,@Param("video") String video,@Param("duration")float duration);
    @Select("select video.vid,project.uid,video.title,video.status,message,video,video.time,IFNULL(testamount, 0) as amount,name from video left join project on project.pid=video.pid left join user_information on user_information.uid=project.uid left join (select video_progress.vid,count(uid) as testamount from video_progress group by vid) as derivedtable on derivedtable.vid=video.vid  where project.pid=#{pid}")
    public Video[] findVideosByPid(@Param("pid") int pid);

    @Select("select count(uid) from video where status=1")
    public int countVideo();

    @Select("select vid from video where pid=#{pid} and status=0 limit 1;")
    public Integer findFirstVidByPid(@Param("pid") int pid);

    @Select("select video from video where vid=#{vid};")
    public String findVideoNameByVid(@Param("vid") int vid);

    @Select("select video.pid,video.vid,video.title,ifnull(video_progress.study_time,0) as study_time,duration from video_progress right join video on video.vid=video_progress.vid and video_progress.uid=#{uid} where pid=#{pid};")
    public SimpleVideoInformation[] findSimpleVideoByPidAndUid(@Param("pid") int pid, @Param("uid")int uid);

    @Delete("delete from video where vid=#{vid};")
    public Boolean dropVideoByVid(@Param("vid") int vid);

    @Update("update video set title=#{title},message=#{message},status=0 where video=#{video}")
    public boolean editVideoByVideo(@Param("title") String title,@Param("message") String message,@Param("video") String video);
}
