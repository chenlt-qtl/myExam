package com.exam.D21_Java_adva.D02_Reflect;

public class Cat {

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private String name;
    private int age;

    public Cat() {
    }

    Cat(String name) {
        this.name = name;
    }

    private Cat(int age) {
        this.age = age;
    }

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void run() {
        System.out.println("阿福跑了。。。");
    }

    public void eat() {
        System.out.println("阿福吃饭了。。。");
    }

    private String eat(String name) {
        return "阿福，吃零食" + name + "了";
    }
}
