package com.exam.o1_principle.o5_openclose;

public class JavaCourse implements ICourse {

    private String name;
    private double price;
    private Integer id;

    public JavaCourse(String name, double price, Integer id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
//        return price * 0.6;
        return price;
    }

}
