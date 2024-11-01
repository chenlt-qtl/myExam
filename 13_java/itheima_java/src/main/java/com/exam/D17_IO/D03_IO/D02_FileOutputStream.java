package com.exam.D17_IO.D03_IO;

import java.io.*;

public class D02_FileOutputStream {
    public static void main(String[] args) throws IOException {
        OutputStream os = new FileOutputStream("fileOutputStream.txt");
        os.write(97);
        os.write('b');
        byte[] bytes = "你好abc".getBytes();
        os.write(bytes);//写入 你好abc
        os.write(bytes,0,6);//写入 你好  一个汉字三个字节
        os.close();

        //追加模式
        OutputStream os1 = new FileOutputStream("fileOutputStream.txt",true);

        //换行
        os1.write("\r\n".getBytes());
        os1.write(99);
        os1.close();
    }
}
