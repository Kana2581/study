package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class VideoComment {
    int uid;

    int cid;
    String name;
    String portrait;
    String comment;
    Timestamp commentTime;
}
