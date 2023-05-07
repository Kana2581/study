package com.example.demo.entity;

import lombok.Data;

@Data
public class SimpleAccountAndUserInformation {
    int uid;
    String account;
    String name;
    String role;
    String birthday;
    String phone;
    String address;

    String portrait;
    String mail;
    String createTime;

}
