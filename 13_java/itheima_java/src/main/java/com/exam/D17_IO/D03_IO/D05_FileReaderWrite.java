package com.exam.D17_IO.D03_IO;

import java.io.*;

public class D05_FileReaderWrite {
    public static void main(String[] args) {
        try (
                Reader reader = new FileReader("fileWriter.txt");
        ) {
            System.out.println("========读文件=======");
            int len;
            while ((len = reader.read()) != -1) {
                System.out.print((char) len);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========写文件=======");

        try (
                Reader reader = new FileReader("fileOutputStream.txt");
        ) {
            int len;
            char[] buffer = new char[1];
            while ((len = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, len));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
                Writer writer = new FileWriter("fileWriter.txt",false);
        ) {
            writer.write(97);
            writer.write('你');
            writer.write("今天吃什么");
            writer.write("\r\n");
            writer.flush();//刷新流
            char[] chars = {'不','知','道','a','b','c'};
            writer.write(chars,0,3);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
