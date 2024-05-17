package com.example.jpa_basic.domain;

import lombok.Data;

//dto : data transfer object
@Data
public class MemberDto {
    private String name;
    private int age;

    public MemberDto(String name, int age){
        this.name = name;
        this.age = age;
    }
}
