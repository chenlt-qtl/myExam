package com.example.netty.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private String name;
    private int age;
    private String address;
}
