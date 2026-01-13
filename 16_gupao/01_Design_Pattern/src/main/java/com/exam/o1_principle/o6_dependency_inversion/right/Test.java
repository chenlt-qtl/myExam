package com.exam.o1_principle.o6_dependency_inversion.right;

public class Test {
    public static void main(String[] args) {
        //依赖抽象，依赖注入的原型
        Tom tom = new Tom();
        tom.study(new JavaCourse());
        tom.study(new PythonCourse());

        System.out.println("====================================================");

        //构造方法注入
        Tom tom1 = new Tom(new JavaCourse());
        Tom tom2 = new Tom(new PythonCourse());
        tom1.study();
        tom2.study();

        System.out.println("====================================================");

        //使用setter
        Tom tom3 = new Tom();
        tom3.setCourse(new PythonCourse());
        tom3.study();
        tom3.setCourse(new JavaCourse());
        tom3.study();

    }
}
