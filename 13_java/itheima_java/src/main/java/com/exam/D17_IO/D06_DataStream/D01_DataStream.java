package com.exam.D17_IO.D06_DataStream;

import java.io.*;

public class D01_DataStream {
    public static void main(String[] args) {
        try (
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("d28_demo.txt"));
                DataInputStream dis = new DataInputStream(new FileInputStream("d28_demo.txt"));
        ) {
            dos.writeInt(97);
            dos.writeDouble(99.5);
            dos.writeUTF("你好啊");
            System.out.println(dis.readInt());
            System.out.println(dis.readDouble());
            System.out.println(dis.readUTF());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
