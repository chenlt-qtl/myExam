package com.exam.o1_principle.o6_dependency_inversion.wrong;

public class Test {
    public static void main(String[] args) {
        Tom tom = new Tom();
        tom.studyJavaCourse();
        tom.studyPythonCourse();
    }
}
