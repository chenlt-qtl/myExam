package com.betta.d40_reflect;

import com.exam.D20_Java_adva.D02_Reflect.Cat;

public class D01_ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        //获取class的三种方法
        Class<Cat> class1 = Cat.class;
        Class<Cat> class2 = (Class<Cat>) Class.forName("com.betta.d40_reflect.Cat");

        Cat student  = new Cat("Tom",7);
        Class<Cat> class3 = (Class<Cat>) student.getClass();


        System.out.println(class1 == class2);
        System.out.println(class1 == class3);

        System.out.println(class1.getName());
    }
}
