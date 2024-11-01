package com.exam.D17_IO.D03_IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class D01_FileInputStream {
    public static void main(String[] args) throws IOException {
        File file = new File("fileInputStream.txt");
        if(!file.exists()){
            file.createNewFile();
        }

        InputStream fileInputStream = new FileInputStream(file);
        int b;
        String str = "";
        while ((b = fileInputStream.read())!=-1){
            str +=(char) b;
        }
        System.out.println(str);
        fileInputStream.close();

        System.out.println("----------------");
        InputStream fileInputStream1 = new FileInputStream(file);
        byte[] buffer = new byte[3];
        while ((b = fileInputStream1.read(buffer))!=-1){
            System.out.print(new String(buffer,0,b));
        }

        fileInputStream1.close();
        System.out.println("");
        System.out.println("一次性全读取");
        InputStream fileInputStream2 = new FileInputStream(file);

        byte[] bytes = fileInputStream2.readAllBytes();
        System.out.println(new String(bytes));
        fileInputStream2.close();


    }
}
