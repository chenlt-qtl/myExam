package com.exam.D20_Java_adva.D02_Reflect;

import com.exam.D20_Java_adva.D02_Reflect.Cat;
import org.junit.Test;

import java.lang.reflect.Field;

public class D03_FieldDemo {

    @Test
    public void testGetFields() throws NoSuchFieldException, IllegalAccessException {
        Class c = Cat.class;

        //获取全部成员变量
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName()+"----------->"+field.getType());
        }
        System.out.println("===============================");

        //获取某个成员变量
        Field name = c.getDeclaredField("name");
        System.out.println(name.getName()+"----------->"+name.getType());
        System.out.println("**********************************");

        //赋值
        Cat cat  = new Cat();
        name.setAccessible(true);
        name.set(cat,"abc");
        System.out.println(cat);

        //取值
        Object o = name.get(cat);
        System.out.println(o);
    }

}
