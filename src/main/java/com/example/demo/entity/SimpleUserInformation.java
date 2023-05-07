package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class SimpleUserInformation {
    int uid;
    String name;
    String portrait;

    @Override
    public String toString() {
        return "SimpleUserInformation{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
