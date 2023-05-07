package com.example.demo.service;

import com.example.demo.mapper.TeacherCodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherCodeService {
    @Resource
    TeacherCodeMapper teacherCodeMapper;
    public boolean deleteByCode(String code)
    {
        return teacherCodeMapper.dropRelationshipByUid(code);
    }
    public boolean generateCode(int num)
    {
        return teacherCodeMapper.callInsertUuids(num);
    }

}
