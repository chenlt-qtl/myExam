package com.exam.o2_pattern.o8_factory.factoryMethod;

import com.exam.o2_pattern.o8_factory.simpleFactory.CourseFactory;

public class Test {
    public static void main(String[] args) {

        ICourseFactory factory = new PythonCourseFactory();
        ICourse course = factory.create();
        course.record();


    }
}
