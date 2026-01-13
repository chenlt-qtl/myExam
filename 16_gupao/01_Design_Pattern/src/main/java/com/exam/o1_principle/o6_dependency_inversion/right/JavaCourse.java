package com.exam.o1_principle.o6_dependency_inversion.right;

public class JavaCourse implements ICourse{
    @Override
    public void study() {
        System.out.println("Tom正在学习JAVA课程");
    }
}
