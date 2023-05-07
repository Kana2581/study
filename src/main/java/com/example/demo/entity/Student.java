package com.example.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;


public class Student extends UserInformation{

    public Student(int uid, String name, Date birthday, String phone, String address, String profile) {
        super(uid, name, birthday, phone, address, profile);
    }
    public Timestamp time;
}
