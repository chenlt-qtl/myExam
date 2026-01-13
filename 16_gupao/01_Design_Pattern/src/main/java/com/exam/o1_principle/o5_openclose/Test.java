package com.exam.o1_principle.o5_openclose;

public class Test {
    public static void main(String[] args) {
        ICourse course = new JavaDiscountCourse("Java架构",11800,1);
        System.out.println("JavaCourse{" +
                "name='" + course.getName() + '\'' +
                ", 现价=" + ((JavaDiscountCourse)course).getDiscountPrice() +
                ", 原价=" + course.getPrice() +
                ", id=" + course.getId() +
                '}');
    }
}
