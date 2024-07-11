package com.betta.interfaceDemo;

import java.util.ArrayList;

public class ClassInfo {

    public ClassInfo() {
        students.add(new Student("张三", '男', 85));
        students.add(new Student("李四", '女', 79));
        students.add(new Student("陈大云", '男', 70));
        students.add(new Student("刘小云", '女', 99));

    }

    ArrayList<Student> students = new ArrayList<>();
    ClassOperator classOperator = new ClassOperatorImpl2();

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    //要求打印出全班学生的信息
    public void printInfo() {
        classOperator.printInfo(students);
    }

    //要求打印出全班学生的平均成绩
    public void printScore() {
        classOperator.printScore(students);
    }
}
