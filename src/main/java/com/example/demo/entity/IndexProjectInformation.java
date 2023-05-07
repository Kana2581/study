package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class IndexProjectInformation {
    int pid;
    int uid;
    String profile;
    String name;
    String cover;

    String portrait;
    Timestamp time;
    String title;
    int amount;
    int status;
}
