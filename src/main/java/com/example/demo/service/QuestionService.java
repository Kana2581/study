package com.example.demo.service;

import com.example.demo.entity.ChoiceQuestion;
import com.example.demo.entity.Question;
import com.example.demo.entity.TrueFalseQuestion;
import com.example.demo.mapper.QuestionMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service
public class QuestionService {
    @Resource
    QuestionMapper questionMapper;
    public boolean addChoiceQuestion(String eid, String title, String optionA, String optionB, String optionC, String optionD, char answer,String notes)
    {
        return questionMapper.loadChoiceQuestion(eid,title,optionA,optionB,optionC,optionD,answer,notes);
    }
    public boolean addTrueFalseQuestion(String eid, String title,char answer,String notes)
    {
        return questionMapper.loadTrueFalseQuestion(eid,title,answer,notes);
    }
    public boolean addQuestion(String eid, String title,String score,String notes)
    {
        return questionMapper.loadQuestion(eid,title,score,notes);
    }

    public ChoiceQuestion[] getChoiceQuestionByEid(String eid)
    {
        return questionMapper.findChoiceQuestion(eid);
    }

    public Question[] getQuestionByEid(String eid)
    {
        return questionMapper.findQuestion(eid);
    }
    public boolean changeChoiceQuestion(String cqid, String title, String optionA, String optionB, String optionC, String optionD, char answer,String notes)
    {

        return questionMapper.editChoiceQuestion(cqid,title,optionA,optionB,optionC,optionD,answer,notes);
    }

    public boolean changeQuestion(String cqid, String title, int score,String notes)
    {

        return questionMapper.editQuestion(cqid,title,score,notes);
    }

    public boolean changeTrueFalseQuestion(String cqid, String title, char answer,String notes)
    {

        return questionMapper.editTrueFalseQuestion(cqid,title,answer,notes);
    }
    public String getEidByCqid(String cqid,String table)
    {

       return questionMapper.findEidByCqid(cqid,table);
    }
    public boolean changeQuestionScore(String eid,int score,String table)
    {

        return questionMapper.editQuestionScore(eid,score,table);
    }
    public TrueFalseQuestion[] getTrueFalseQuestionByEid(String eid)
    {
        return questionMapper.findTrueFalseQuestion(eid);
    }
    public boolean deleteChoiceQuestion(String cqid,String table)
    {
        return questionMapper.deleteChoiceQuestion(cqid,table);
    }

}
