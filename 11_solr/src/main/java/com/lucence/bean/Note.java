package com.lucence.bean;


import lombok.Data;

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
