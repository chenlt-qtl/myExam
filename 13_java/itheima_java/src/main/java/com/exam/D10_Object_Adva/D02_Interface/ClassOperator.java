package com.exam.D10_Object_Adva.D02_Interface;

import java.util.ArrayList;

public interface ClassOperator {
    //打印出全班学生的信息
    void printInfo(ArrayList<Student> students);

    //打印出全班学生的平均成绩
    void printScore(ArrayList<Student> students);
}
