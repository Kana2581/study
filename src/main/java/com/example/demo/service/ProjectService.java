package com.example.demo.service;

import com.example.demo.entity.IndexProjectInformation;
import com.example.demo.entity.SimpleAccountAndUserInformation;
import com.example.demo.entity.UserInformation;
import com.example.demo.entity.Video;
import com.example.demo.mapper.ProjectMapper;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

@EnableTransactionManagement
@Service
public class ProjectService {
    @Resource
    UploadFileService uploadFileService;
    @Resource
    ProjectMapper projectMapper;
    @Transactional
    public void addProject(int uid,String base64Str,String title,String profile,int status) {

        String fileName= RandomNameUtil.createRandomName() +".jpeg";
        uploadFileService.addImage(base64Str, FileUtil.coversPath+fileName);
        projectMapper.loadProject(title,fileName,profile,status,uid);
    }
    public IndexProjectInformation[] getProjectsByPage(int page,String search)
    {
        return projectMapper.findProjectsByPage((page-1)*12,search);
    }
    public IndexProjectInformation[] getProjectsByUid(int uid)
    {
        return projectMapper.findProjectsByUid(uid);
    }

    public IndexProjectInformation[] getCoursesHistoryByUid(int uid)
    {
        return projectMapper.findCoursesHistoryByUid(uid);
    }
    public boolean deleteProjectByPid(int pid,String uid)
    {
        return projectMapper.dropProjectByPid(pid,uid);
    }
    public IndexProjectInformation getProjectByPid(int pid)
    {
        return projectMapper.findProjectByPid(pid);
    }
    public int getProjectsNums()
    {
        return projectMapper.countProjects();
    }
    public SimpleAccountAndUserInformation[] getSimpleAccountAndUserInformation()
    {
        return projectMapper.findSimpleAccountAndUserInformation();
    }

    public int getUidByPid(int pid)
    {
        return projectMapper.findUidByPid(pid);
    }
    public void changeProject(String title,String profile,int status,String base64Str,int pid,String uid)
    {
        try {
            changeCover(base64Str,pid,uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        projectMapper.editProjectByTitleAndProfile(title,profile,status,pid,uid);
    }

    public void changeProject(String title,String profile,int status,int pid,String uid)
    {
        projectMapper.editProjectByTitleAndProfile(title,profile, status,pid,uid);
    }
    @Transactional
    public void changeCover(String base64Str,int pid,String uid) throws SQLException {
        IndexProjectInformation information=getProjectByPid(pid);
        if(information!=null)
            FileUtil.deleteFile(FileUtil.coversPath+information.getCover());
        String fileName= RandomNameUtil.createRandomName() +".jpeg";
        uploadFileService.addImage(base64Str,FileUtil.coversPath+fileName);
        projectMapper.editCover(fileName,pid,uid);
    }
}
