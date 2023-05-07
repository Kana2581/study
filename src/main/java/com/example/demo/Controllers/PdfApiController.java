package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.service.PdfService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/pdf/api")
@RestController
public class PdfApiController {
    @Resource
    PdfService pdfService;
    @PostMapping("/up-pdf-information")
    public RestBean upLoadVideo(@RequestParam("pdf") MultipartFile pdf, @RequestParam("pid") int pid, @RequestParam("title") String title) {



        String name= RandomNameUtil.createRandomName()+".pdf";
        pdfService.addPdf(pid,pdf,name,title);
        return new RestBean(233,"hah");

    }
    @GetMapping("/pdf-page")
    public RestBean LoadVideoInformation(@RequestParam(value = "pid") int pid)
    {
        return new RestBean(200,"hah",pdfService.loadPdfsByPid(pid));
    }
}
