package com.example.demo.utils;

import javax.servlet.http.Cookie;
import java.sql.SQLException;

public class CookieUtil {
    public static String getCookieFromCookies(Cookie[] cookies,String name)
    {
        for(Cookie cookie:cookies)
        {
            if(cookie.getName().equals(name)) {
                return cookie.getValue();
            }

        }
        return null;
    }
}
