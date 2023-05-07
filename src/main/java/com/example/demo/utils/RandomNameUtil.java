package com.example.demo.utils;

import java.util.Random;

public class RandomNameUtil {
    public static String createRandomName()
    {

        Random random=new Random();
        return System.currentTimeMillis()+"@"+(random.nextInt(8999)+1000);
    }
    public static String generateRandomCode(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(letters.length());
            char c = letters.charAt(index);
            code.append(c);
        }
        return code.toString();
    }
}
