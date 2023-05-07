package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class VideoReply {
    int rid;
    int uid;

    int cid;
    String name;
    String portrait;
    String reply;
    Timestamp replyTime;
}
