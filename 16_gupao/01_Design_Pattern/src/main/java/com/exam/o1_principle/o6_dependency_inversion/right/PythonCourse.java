package com.exam.o1_principle.o6_dependency_inversion.right;

public class PythonCourse implements ICourse{
    @Override
    public void study() {
        System.out.println("Tom正在学习Python课程");
    }
}
