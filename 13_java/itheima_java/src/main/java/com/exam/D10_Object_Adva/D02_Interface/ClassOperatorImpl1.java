package com.exam.D10_Object_Adva.D02_Interface;

import java.util.ArrayList;

public class ClassOperatorImpl1 implements ClassOperator {
    @Override
    public void printInfo(ArrayList<Student> students) {
        System.out.println("------------全班全部学生信息如下-------------");
        students.forEach(System.out::println);
        System.out.println("-----------------------------------------");
    }

    @Override
    public void printScore(ArrayList<Student> students) {
        double total = 0;
        for (Student student : students) {
            total+= student.getScore();
        }
        System.out.println("------------全班成绩如下-------------");
        System.out.println("平均分:"+total/students.size());
        System.out.println("-----------------------------------------");
    }
}
