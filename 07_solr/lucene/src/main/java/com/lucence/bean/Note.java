package com.lucence.bean;


import lombok.Data;

/**
 * 用来测试中文
 */
@Data
public class Note {
    private java.lang.String id;

    private java.lang.String name;

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
