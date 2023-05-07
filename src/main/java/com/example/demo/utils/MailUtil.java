package com.example.demo.utils;

public class MailUtil {
    public static String generateMailText(String code)
    {
        return "<h2>尊敬的用户：</h2>" +
                "<p>您的验证码是：<strong>" + code + "</strong></p>" +
                "<p>请在页面中输入此验证码以验证您的身份。</p><p>" +BibleUtil.getBibleVerse()+
                "</p><img src='https://cdn.pixabay.com/photo/2017/05/12/08/29/coffee-2306471_960_720.jpg'/>";
    }
}
