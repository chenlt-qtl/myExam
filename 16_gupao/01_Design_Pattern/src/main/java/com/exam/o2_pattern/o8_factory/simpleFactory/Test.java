package com.exam.o2_pattern.o8_factory.simpleFactory;

public class Test {
    public static void main(String[] args) {

//        ICourse java = new CourseFactory().create("java");
//        java.record();
        ICourse java = new CourseFactory().create("com.exam.o2_pattern.o8_factory.simpleFactory.JavaCourse");
        java.record();

        ICourse python = new CourseFactory().create(PythonCourse.class);
        python.record();

    }
}
