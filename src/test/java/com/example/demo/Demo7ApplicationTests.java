package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@SpringBootTest
class Demo7ApplicationTests {
    @Resource
    JavaMailSender sender;

   // @Test
    void contextLoads() {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("Test");
        mailMessage.setText("uuid");
        mailMessage.setFrom("q2695945997@163.com");
        mailMessage.setTo("2695945997@qq.com");
        sender.send(mailMessage);
    }

}
