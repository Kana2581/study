package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Pdf {

    int cwid;
    String pdf;
    String title;
    Timestamp time;
}
