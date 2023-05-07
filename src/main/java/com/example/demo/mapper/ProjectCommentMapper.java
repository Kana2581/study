package com.example.demo.mapper;

import com.example.demo.entity.VideoComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface ProjectCommentMapper {
    @Insert("insert into project_comment(uid,pid,comment) value(#{uid},#{pid},#{comment})")
    public Boolean addProjectComment(@Param("uid") int uid, @Param("pid")int pid, @Param("comment")String comment);
    @Select("select name,portrait,comment,comment_time from user_information,project_comment where pid=#{pid} and user_information.uid=project_comment.uid")
    public VideoComment[] findProjectComment(@Param("pid")int pid);
}
