package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Video {
    int vid;
    int uid;
    String name;
    String title;
    String message;

    String video;
    Timestamp time;
    int amount;
    int status;


}
