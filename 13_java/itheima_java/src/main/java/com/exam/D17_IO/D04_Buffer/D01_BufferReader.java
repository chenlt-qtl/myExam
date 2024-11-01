package com.exam.D17_IO.D04_Buffer;

import java.io.*;

public class D01_BufferReader {
    public static void main(String[] args) {
        try (
                Reader rd = new FileReader("fileWriter.txt");
                BufferedReader bis = new BufferedReader(rd);
        ) {

            String str;
            while ((str = bis.readLine()) != null) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
