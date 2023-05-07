package com.example.demo.entity;

import lombok.Data;

import java.sql.Date;


@Data
public class UserInformation {
    int uid;
    String name;
    Date birthday;
    String phone;
    String address;
    String portrait;
    String profile;


    public UserInformation(int uid, String name, Date birthday, String phone, String address, String profile) {
        this.uid = uid;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.profile = profile;
    }
}
