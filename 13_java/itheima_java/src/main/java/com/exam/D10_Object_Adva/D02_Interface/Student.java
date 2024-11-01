package com.exam.D10_Object_Adva.D02_Interface;

public class Student {

    public Student(String name, char sex, double score) {
        this.name = name;
        this.sex = sex;
        this.score = score;
    }

    public Student() {
    }

    private String name;
    private char sex;
    private double score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "姓名：" + name +
                ", 性别： " + sex +
                ", 成绩：" + score;
    }
}
