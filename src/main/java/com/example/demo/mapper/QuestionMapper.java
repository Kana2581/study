package com.example.demo.mapper;

import com.example.demo.entity.ChoiceQuestion;
import com.example.demo.entity.Question;
import com.example.demo.entity.TrueFalseQuestion;
import org.apache.ibatis.annotations.*;

@Mapper
public interface QuestionMapper {
    @Insert("insert into choice_question(eid,title,optionA,optionB,optionC,optionD,answer,notes) value(#{eid},#{title},#{optionA},#{optionB},#{optionC},#{optionD},#{answer},#{notes})")
    public boolean loadChoiceQuestion(@Param("eid")String eid,@Param("title")String title,@Param("optionA")String optionA,@Param("optionB")String optionB,@Param("optionC")String optionC,@Param("optionD")String optionD,@Param("answer")char answer,@Param("notes")String notes);
    @Insert("insert into true_false_question(eid,title,answer,notes) value(#{eid},#{title},#{answer},#{notes})")
    public boolean loadTrueFalseQuestion(@Param("eid")String eid,@Param("title")String title,@Param("answer")char answer,@Param("notes")String notes);
    @Insert("insert into question(eid,title,score,notes) value(#{eid},#{title},#{score},#{notes})")
    public boolean loadQuestion(@Param("eid")String eid,@Param("title")String title,@Param("score")String score,@Param("notes")String notes);
    @Select("select * from choice_question where eid=#{eid}")
    public ChoiceQuestion[] findChoiceQuestion(@Param("eid")String eid);
    @Select("select * from true_false_question where eid=#{eid}")
    public TrueFalseQuestion[] findTrueFalseQuestion(@Param("eid")String eid);

    @Select("select * from question where eid=#{eid}")
    public Question[] findQuestion(@Param("eid")String eid);

    @Select("select eid from ${table} where cqid=#{cqid}")
    public String findEidByCqid(@Param("cqid")String cqid,@Param("table")String table);
    @Delete("delete from ${table} where cqid=#{cqid}")
    public boolean deleteChoiceQuestion(@Param("cqid")String cqid,@Param("table")String table);
    @Update("update ${table} set score=#{score} where eid=#{eid}")
    public boolean editQuestionScore(@Param("eid")String eid,@Param("score")int score,@Param("table")String table);
    @Update("update question set title=#{title},score=#{score},notes=#{notes} where cqid=#{cqid}")
    public boolean editQuestion(@Param("cqid")String cqid,@Param("title")String title,@Param("score")int score,@Param("notes")String notes);

    @Update("update true_false_question set title=#{title},answer=#{answer},notes=#{notes} where cqid=#{cqid}")
    public boolean editTrueFalseQuestion(@Param("cqid")String cqid,@Param("title")String title,@Param("answer")char answer,@Param("notes")String notes);
    @Update("update choice_question set title=#{title},optionA=#{optionA},optionB=#{optionB},optionC=#{optionC},optionD=#{optionD},answer=#{answer},notes=#{notes} where cqid=#{cqid}")
    public boolean editChoiceQuestion(@Param("cqid")String cqid,@Param("title")String title,@Param("optionA")String optionA,@Param("optionB")String optionB,@Param("optionC")String optionC,@Param("optionD")String optionD,@Param("answer")char answer,@Param("notes")String notes);
}
