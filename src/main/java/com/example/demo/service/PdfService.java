package com.example.demo.service;

import com.example.demo.entity.Pdf;
import com.example.demo.entity.Video;
import com.example.demo.mapper.PdfMapper;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
@Service
@EnableTransactionManagement
public class PdfService {
    @Resource
    UploadFileService uploadFileService;
    @Resource
    PdfMapper pdfMapper;
    @Transactional
    public void addPdf(int pid, MultipartFile pdf, String name,String title)
    {

        uploadFileService.addPdf(pdf,name);
        pdfMapper.addPdf(pid,name,title);
    }
    public Pdf[] loadPdfsByPid(int pid)
    {
        return pdfMapper.findPdfsByPid(pid);
    }

}
