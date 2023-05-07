package com.example.demo.mapper;

import com.example.demo.entity.VideoComment;
import com.example.demo.entity.VideoReply;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VideoReplyMapper {
    @Insert("insert into video_reply(uid,cid,reply) value(#{uid},#{cid},#{reply})")
    public Boolean addVideoReply(@Param("uid") int uid, @Param("cid")int cid, @Param("reply")String reply);
    @Select("select rid,name,cid,portrait,reply,reply_time,video_reply.uid from user_information,video_reply where user_information.uid=video_reply.uid and cid=#{cid}")
    public VideoReply[] findVideoReply(@Param("cid")int cid);

    @Delete("delete from video_reply where rid=#{rid}")
    public Boolean deleteVideoReply(@Param("rid")int rid);
}
