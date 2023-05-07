package com.example.demo.mapper;

import com.example.demo.entity.VideoComment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VideoCommentMapper {
    @Insert("insert into video_comment(uid,vid,comment) value(#{uid},#{vid},#{comment})")
    public Boolean addVideoComment(@Param("uid") int uid, @Param("vid")int vid,@Param("comment")String comment);
    @Select("select name,cid,portrait,comment,comment_time,video_comment.uid from user_information,video_comment where user_information.uid=video_comment.uid and vid=#{vid}")
    public VideoComment[] findVideoComment(@Param("vid")int vid);
    @Delete("delete from video_comment where cid=#{cid}")
    public Boolean deleteVideoComment(@Param("cid")int cid);
}
