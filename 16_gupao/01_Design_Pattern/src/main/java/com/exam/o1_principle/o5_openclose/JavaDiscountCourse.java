package com.exam.o1_principle.o5_openclose;

public class JavaDiscountCourse extends JavaCourse{

    public JavaDiscountCourse(String name, double price, Integer id) {
        super(name, price, id);
    }

    public Double getDiscountPrice() {
        return super.getPrice() * 0.6;
    }
}
