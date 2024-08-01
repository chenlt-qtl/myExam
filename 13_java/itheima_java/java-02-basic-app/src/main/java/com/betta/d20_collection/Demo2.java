package com.betta.d20_collection;

import com.betta.d17_Arrays.Student;

import java.util.Set;
import java.util.TreeSet;

/**
 * Set
 */
public class Demo2 {

    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();//基础类型数据按升序排序 不重复，无序
        set.add(8);
        set.add(3);
        set.add(5);
        set.add(5);
        set.add(9);
        System.out.println(set);//[3, 5, 8, 9]


        //自定义类型要指定比较器
        Set<Student> set1  = new TreeSet<>(Student::compareTo);
        set1.add(new Student("Tom",19,156));
        set1.add(new Student("Mike",13,166));
        set1.add(new Student("Joey",12,178));
        set1.add(new Student("Monica",10,165));
        set1.add(new Student("Chandler",18,178));
        System.out.println(set1);
    }
}
