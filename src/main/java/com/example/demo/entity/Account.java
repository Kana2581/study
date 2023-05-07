package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Account {
    int uid;
    String account;
    String password;
    String mail;
    Timestamp createTime;
    String role;
}
