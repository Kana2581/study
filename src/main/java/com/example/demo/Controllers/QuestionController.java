package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.service.QuestionService;
import com.example.demo.utils.CookieUtil;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/question/api")
@RestController
public class QuestionController {
    @Resource
    QuestionService questionService;
    @PostMapping("/up-choice-question")
    public RestBean upChoiceQuestion(@RequestParam("eid")String eid,@RequestParam("title")String title, @RequestParam("optionA")String optionA, @RequestParam("optionB")String optionB, @RequestParam("optionC")String optionC,@RequestParam("optionD")String optionD,@RequestParam("answer")char answer,@RequestParam("notes")String notes)
    {
        return new RestBean(200,"hah",questionService.addChoiceQuestion(eid,title,optionA,optionB,optionC,optionD,answer,notes));
    }
    @PostMapping("/up-true-false-question")
    public RestBean upTrueFalseQuestion(@RequestParam("eid")String eid,@RequestParam("title")String title,@RequestParam("answer")char answer,@RequestParam("notes")String notes)
    {
        return new RestBean(200,"hah",questionService.addTrueFalseQuestion(eid,title,answer,notes));
    }
    @PostMapping("/up-question")
    public RestBean upTrueFalseQuestion(@RequestParam("eid")String eid,@RequestParam("title")String title,@RequestParam("score")String score,@RequestParam("notes")String notes)
    {
        return new RestBean(200,"hah",questionService.addQuestion(eid,title,score,notes));
    }
    @GetMapping("/choice-question-list")
    public RestBean choiceQuestion(@RequestParam("eid")String eid)
    {
        return new RestBean(200,"hah",questionService.getChoiceQuestionByEid(eid));
    }
    @GetMapping("/true-false-question-list")
    public RestBean trueFalseQuestion(@RequestParam("eid")String eid)
    {
        return new RestBean(200,"hah",questionService.getTrueFalseQuestionByEid(eid));
    }
    @GetMapping("/question-list")
    public RestBean question(@RequestParam("eid")String eid)
    {
        return new RestBean(200,"hah",questionService.getQuestionByEid(eid));
    }
    @PostMapping("/edit-choice-question")
    public void editChoiceQuestion(@RequestParam("cqid")String cqid,@RequestParam("title")String title, @RequestParam("optionA")String optionA, @RequestParam("optionB")String optionB, @RequestParam("optionC")String optionC,@RequestParam("optionD")String optionD,@RequestParam("answer")char answer,@RequestParam("notes")String notes,HttpServletResponse response)
    {

        try {
            response.sendRedirect("/question-management?eid="+questionService.getEidByCqid(cqid,"choice_question")+"&add="+questionService.changeChoiceQuestion(cqid,title,optionA,optionB,optionC,optionD,answer,notes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/edit-question")
    public void editQuestion(@RequestParam("cqid")String cqid,@RequestParam("title")String title,@RequestParam("score")int score,@RequestParam("notes")String notes,HttpServletResponse response)
    {

        try {
            response.sendRedirect("/question-management?eid="+questionService.getEidByCqid(cqid,"question")+"&add="+questionService.changeQuestion(cqid,title,score,notes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/edit-true-false-question")
    public void editTrueFalseQuestion(@RequestParam("cqid")String cqid,@RequestParam("title")String title,@RequestParam("answer")char answer,@RequestParam("notes")String notes,HttpServletResponse response)
    {

        try {
            response.sendRedirect("/question-management?eid="+questionService.getEidByCqid(cqid,"true_false_question")+"&add="+questionService.changeTrueFalseQuestion(cqid,title,answer,notes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edit-question-score")
    public RestBean editChoiceQuestionScore(@RequestParam("eid")String eid,@RequestParam("score")int score,@RequestParam("table")String table ){


           return new RestBean(200,"hah",questionService.changeQuestionScore(eid,score,table));


    }
    @GetMapping("/delete-question")
    public RestBean deleteChoiceQuestion(@RequestParam("cqid")String cqid ,@RequestParam("table")String table)
    {
        return new RestBean(200,"success",questionService.deleteChoiceQuestion(cqid,table)) ;

    }


}
