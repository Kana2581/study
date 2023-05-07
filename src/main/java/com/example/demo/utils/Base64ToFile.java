package com.example.demo.utils;

import java.util.Base64;
public class Base64ToFile {
    public static byte[] convert(String base64Str)
    {

        return Base64.getDecoder().decode(base64Str.substring(23));
    }
}
