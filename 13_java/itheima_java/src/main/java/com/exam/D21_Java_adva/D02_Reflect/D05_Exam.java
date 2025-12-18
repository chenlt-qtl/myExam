package com.exam.D21_Java_adva.D02_Reflect;


import com.exam.D11_API.D03_Arrays.D03.Student;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 * 对于任意一个对象，该框架都可以把对象的字段名和对应的值，保存到文件中去
 */
public class D05_Exam {
    public static void main(String[] args) {
        Student s1 = new Student("阿福", 2, 20);
        Cat c1 = new Cat("哈哈", 5);
        saveObject(s1);
        saveObject(c1);
    }

    public static void saveObject(Object obj) {
        Class c = obj.getClass();
        try (
                PrintStream ps = new PrintStream(new FileOutputStream("file\\D05_exam.txt", true));
        ) {
            ps.println("---------" + c.getSimpleName() + "--------");
            //获取全部成员变量
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String s = field.get(obj) + "";
                ps.println(name + "-->" + s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
