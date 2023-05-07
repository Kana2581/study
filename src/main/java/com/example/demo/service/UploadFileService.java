package com.example.demo.service;

import com.example.demo.utils.Base64ToFile;
import com.example.demo.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;


@Service
public class UploadFileService {
    public void addVideo(MultipartFile video, String file)
    {
        try {
            FileUtil.saveFile(video.getBytes(),FileUtil.videosPath+file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addPdf(MultipartFile pdf, String file)
    {
        try {
            FileUtil.saveFile(pdf.getBytes(),FileUtil.pdfsPath+file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addImage(String base64Str,String file)
    {

        FileUtil.saveFile(Base64ToFile.convert(base64Str),file);

    }

}
