package com.example.demo.entity;

import lombok.Data;

@Data
public class Question {
    int cqid;
    int eid;
    String title;
    String notes;
    int score;
}
