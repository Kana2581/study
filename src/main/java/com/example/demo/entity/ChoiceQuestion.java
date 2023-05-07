package com.example.demo.entity;

import lombok.Data;

@Data
public class ChoiceQuestion extends Question{
    String optionA;
    String optionB;
    String optionC;
    String optionD;

    char answer;



}
