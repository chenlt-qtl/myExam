package com.exam.D10_Object_Adva.D02_Interface;

import java.util.ArrayList;

public class ClassOperatorImpl2 implements ClassOperator {
    @Override
    public void printInfo(ArrayList<Student> students) {
        System.out.println("------------全班全部学生信息如下-------------");
        int count1 = 0;//男
        int count2 = 0;//女
        for (Student student : students) {
            System.out.println(student);
            if (student.getSex() == '男') {
                count1++;
            } else {
                count2++;
            }
        }
        System.out.println("男生人数:" + count1 + "; 女生人数：" + count2);
        System.out.println("-----------------------------------------");
    }

    @Override
    public void printScore(ArrayList<Student> students) {
        double total = 0, max = 0, min = 100;
        for (Student student : students) {
            total += student.getScore();
            max = student.getScore() > max ? student.getScore() : max;
            min = student.getScore() < min ? student.getScore() : min;
        }
        System.out.println("最高分：" + max + "; 最低分：" + min);
        total = total - max - min;//去掉最高分 去掉最低分
        System.out.println("------------全班成绩如下-------------");
        System.out.println("平均分:" + total / (students.size() - 2));
        System.out.println("-----------------------------------------");
    }
}
