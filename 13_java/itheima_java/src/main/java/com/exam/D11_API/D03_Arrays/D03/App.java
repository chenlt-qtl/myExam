package com.exam.D11_API.D03_Arrays.D03;

/**
 * 构造器引用
 */
public class App {
    public static void main(String[] args) {

//        CreateStudent cs = new CreateStudent() {
//            @Override
//            public Student create(String name, int age, double height) {
//                return new Student(name,age,height);
//            }
//        };

        CreateStudent cs = Student::new;
        Student student = cs.create("钱老板", 55, 167.4);
        System.out.println(student);
    }
}

