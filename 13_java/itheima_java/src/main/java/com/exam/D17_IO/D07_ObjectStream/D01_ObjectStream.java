package com.exam.D17_IO.D07_ObjectStream;

import java.io.*;

public class D01_ObjectStream {
    public static void main(String[] args) {
        //创建对象
        User user = new User("张三", 32);
        try (
                //创建对象字节输出流
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d29_objectStream.txt"));
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d29_objectStream.txt"));
        ) {
            oos.writeObject(user);
            System.out.println(ois.readObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

