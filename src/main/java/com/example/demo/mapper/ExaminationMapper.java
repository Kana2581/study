package com.example.demo.mapper;

import com.example.demo.entity.Examination;
import com.example.demo.entity.IndexProjectInformation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ExaminationMapper {
    @Insert("insert into examination(title,start_time,dead_time,pid) value(#{title},#{startTime},#{deadTime},#{pid})")
    public Boolean addExamination(@Param("title")String title,@Param("startTime") String startTime, @Param("deadTime") String deadTime,@Param("pid")String pid);
    @Select("select * from examination where pid=#{pid}")
    public Examination[] findExaminationsByPid(@Param("pid") String pid);

    @Select("select * from examination where eid=#{eid}")
    public Examination findExaminationsByEid(@Param("eid") String eid);

    @Update("update examination set title=#{title},start_time=#{startTime},dead_time=#{deadTime} where eid=#{eid}")
    public Boolean editExamination(@Param("title")String title,@Param("startTime") String startTime, @Param("deadTime") String deadTime,@Param("eid")String eid);

}
