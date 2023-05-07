package com.example.demo.mapper;

import com.example.demo.entity.Student;
import com.example.demo.entity.UserInformation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RelationshipMapper {
    @Insert("insert into class_relationship(student,teacher) value(#{studentUid},#{teacherUid})")
    public boolean loadRelationshipByUid(@Param("studentUid")String studentUid,@Param("teacherUid")String teacherUid);
    @Select("select user_information.uid,name,birthday,phone,address,portrait,profile,time from user_information,class_relationship where teacher=#{teacher} and uid=student; ")
    public Student[] findStudentByTeacherUid(@Param("teacher")String teacher);
    @Select("select user_information.uid,name,birthday,phone,address,portrait,profile,time from user_information,class_relationship where student=#{student} and uid=teacher; ")
    public Student[] findTeacherByTeacherUid(@Param("teacher")String student);

    @Delete("delete from class_relationship where student=#{studentUid} and teacher=#{teacherUid}; ")
    public boolean dropRelationshipByUid(@Param("studentUid")String studentUid,@Param("teacherUid")String teacherUid);
}
