package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherCodeMapper {
    @Delete("delete from teacher_uuid_code where uuid=#{code}")
    public boolean dropRelationshipByUid(@Param("code")String code);
    @Select("call insert_uuids(${}) from teacher_uuid_code where uuid=#{code}")
    public boolean callInsertUuids(@Param("num")int num);
}
