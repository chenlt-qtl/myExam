package com.exam.D15_Collections;

import com.exam.D11_API.D03_Arrays.D03.Student;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set
 */
public class D03_Set {

    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();//基础类型数据按升序排序 不重复，无序
        set.add(8);
        set.add(3);
        set.add(5);
        set.add(5);
        set.add(9);
        System.out.println(set);//[3, 5, 8, 9]

        LinkedHashSet<Integer> set1 = new LinkedHashSet<>();//有序 不重复 无索引
        set1.add(8);
        set1.add(3);
        set1.add(5);
        set1.add(6);
        set1.add(5);
        set1.add(9);
        System.out.println(set1);//[8, 3, 5, 6, 9]


        //自定义类型要指定比较器
        Set<Student> set2 = new TreeSet<>(Student::compareTo);
        set2.add(new Student("Tom", 19, 156));
        set2.add(new Student("Mike", 13, 166));
        set2.add(new Student("Joey", 12, 178));
        set2.add(new Student("Monica", 10, 165));
        set2.add(new Student("Chandler", 18, 178));
        System.out.println(set2);
    }
}
