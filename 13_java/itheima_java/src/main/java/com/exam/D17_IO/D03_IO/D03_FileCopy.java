package com.exam.D17_IO.D03_IO;

import java.io.*;

public class D03_FileCopy {
    public static void main(String[] args) throws IOException {
        //把 123.jpg复制到 456.jpg
        InputStream is = new FileInputStream("123.jpg");
        OutputStream os = new FileOutputStream("456.jpg");
        byte[] buffer = new byte[10];
        int len;
        while ((len = is.read(buffer))!=-1){
            os.write(buffer,0,len);
        }
        is.close();
        os.close();
    }
}
