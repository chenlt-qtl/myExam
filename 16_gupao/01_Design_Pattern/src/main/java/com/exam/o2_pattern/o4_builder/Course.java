package com.exam.o2_pattern.o4_builder;

import lombok.Data;

@Data
public class Course {
    private String name;
    private String ppt;
    private String video;
    private String note;
    private String homework;

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", ppt='" + ppt + '\'' +
                ", video='" + video + '\'' +
                ", note='" + note + '\'' +
                ", homework='" + homework + '\'' +
                '}';
    }
}
