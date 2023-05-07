package com.example.demo.utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class BibleUtil {
    public static String getBibleVerse() {
        try {
            // 请求每日圣经网站
            Document doc = Jsoup.connect("https://dailyverses.net/").get();

            // 获取每日经文所在的div元素
            Element verseDiv = doc.select("div.b1").first();

            // 获取经文内容
            Element verseContent = verseDiv.select("span.v1").first();
            String content = verseContent.text();



            // 输出结果
            return content;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
