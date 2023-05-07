package com.example.demo.service;

import com.example.demo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class MailVerityService {
    @Resource
    StringRedisTemplate template;
    @Value("${spring.mail.username}")
    String username;
    @Resource
    JavaMailSender sender;
    public void verity(String mail)
    {

        MimeMessage mimeMessage=sender.createMimeMessage();
        try {
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
            mailMessage.setSubject("[精品视频]验证码");

            mailMessage.setFrom(username);
            mailMessage.setTo(mail);
            Random random=new Random();
            String code=String.valueOf(random.nextInt(899999)+100000);
            mailMessage.setText(MailUtil.generateMailText(code),true);

            template.opsForValue().set("mail"+mail,code,3, TimeUnit.MINUTES);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
