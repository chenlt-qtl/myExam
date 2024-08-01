package com.betta.d17_Arrays;

import java.util.Arrays;

public class Demo2 {
    public static void main(String[] args) {
        Student[] students = new Student[4];
        students[0] = new Student("孙悟空", 5000, 170.5);
        students[1] = new Student("八戒", 3000, 177.6);
        students[2] = new Student("沙和尚", 6000, 179.6);
        students[3] = new Student("唐僧", 20, 183.3);

        //按年龄进行排序
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));

        //按身高排序
        Arrays.sort(students, (o1, o2) -> Double.compare(o1.getHeight(), o2.getHeight()));

        //静态方法引用
        Arrays.sort(students, CompareStudent::compareByAge);

        //实例方法引用
        CompareStudent compareStudent = new CompareStudent();
        Arrays.sort(students, compareStudent::compareByHeight);

        //特定类型方法引用
        String[] names = {"Tom","mike","Mary","andy"};
        Arrays.sort(names, String::compareToIgnoreCase);

        System.out.println(Arrays.toString(names));

        //构造器引用
    }
}
