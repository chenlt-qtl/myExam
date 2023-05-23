package com.exam.batch.entity;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;
    private int age;
    private String sex;
    private String address;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
