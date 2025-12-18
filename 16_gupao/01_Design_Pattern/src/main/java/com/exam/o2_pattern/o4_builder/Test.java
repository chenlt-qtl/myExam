package com.exam.o2_pattern.o4_builder;

public class Test {
    public static void main(String[] args) {
        CourseBuilder build = new CourseBuilder();
        build.addName("test")
                .addPpt("ppt")
                .addHomework("homework")
                .addVideo("video")
                .addNote("note");

        Course course = build.build();
        System.out.println(course);

    }
}
