package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Project {
    int pid;
    int uid;
    String title;
    String profile;
    String cover;

    Timestamp time;
}
