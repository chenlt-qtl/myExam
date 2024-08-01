package com.betta.d20_collection;

import com.betta.d17_Arrays.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StreamDemo1 {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("张三丰", 18, 178));
        list.add(new Student("李安", 55, 167));
        list.add(new Student("刘和白", 32, 163));
        list.add(new Student("李遥月", 37, 177));
        list.add(new Student("李遥月", 34, 172));
        //找到年龄在30-40岁之间，并按年龄升序输出
        List<Student> collect = list.stream().filter(i -> i.getAge() >= 30 && i.getAge() <= 40)
                .sorted((a, b) -> a.getAge() - b.getAge()).collect(Collectors.toList());
        System.out.println(collect);

        //取出身高最高的前三个学生
        collect = list.stream().sorted((a, b) -> Double.compare(b.getHeight(), a.getHeight()))
                .limit(3).collect(Collectors.toList());
        System.out.println(collect);

        //取出身高倒数2名学生
        collect = list.stream().sorted((a, b) -> Double.compare(b.getHeight(), a.getHeight()))
                .skip(list.size() - 2).collect(Collectors.toList());
        System.out.println(collect);

        System.out.println("-------------------");
        //终结流
        //身高超过168，名字去重后输出
        list.stream().filter(i -> i.getHeight() >= 168)
                .map(Student::getName).distinct().forEach(System.out::println);

        //身高超过168有几人
        System.out.println(list.stream().filter(i -> i.getHeight() >= 168).count());

        //身高最高的学生
        System.out.println(list.stream().max(((o1, o2) -> Double.compare(o1.getHeight(),o2.getHeight()))).get());

        //身高最矮的学生
        System.out.println(list.stream().max(((o1, o2) -> Double.compare(o2.getHeight(),o1.getHeight()))).get());

        //找出身高超过170的学生
        System.out.println(list.stream().filter(o->o.getHeight()>=170).collect(Collectors.toList()));

        //找出身高超过170的学生，并把学生的名字和身高放到一个Map中返回
        Map<String,Double> result = list.stream().filter(o->o.getHeight()>=175).collect(Collectors.toMap(o->o.getName(),o->o.getHeight()));
        System.out.println(result);

        //找出身高超过170的学生，存到数组中
        Student[] array = list.stream().filter(o -> o.getHeight() >= 175).toArray(s -> new Student[s]);
        System.out.println(Arrays.toString(array));
    }
}
