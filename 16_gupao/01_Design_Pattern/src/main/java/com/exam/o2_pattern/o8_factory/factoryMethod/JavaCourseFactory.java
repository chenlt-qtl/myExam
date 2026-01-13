package com.exam.o2_pattern.o8_factory.factoryMethod;


public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}
