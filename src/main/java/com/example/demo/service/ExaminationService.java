package com.example.demo.service;

import com.example.demo.entity.Examination;
import com.example.demo.mapper.ExaminationMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ExaminationService {
    @Resource
    ExaminationMapper examinationMapper;
    public boolean addExamination(String title, String start, String end,String pid)
    {
        return examinationMapper.addExamination(title,start,end,pid);

    }
    public boolean changeExamination(String title, String start, String end,String eid)
    {
        return examinationMapper.editExamination(title,start,end,eid);

    }
    public Examination[] getExaminationByPid(String pid)
    {
        return examinationMapper.findExaminationsByPid(pid);
    }
    public Examination getExaminationByEid(String eid)
    {
        return examinationMapper.findExaminationsByEid(eid);
    }
}
