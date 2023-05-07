package com.example.demo.entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class Examination {
    int pid;
    int eid;
    String title;
    String startTime;
    String deadTime;

}
