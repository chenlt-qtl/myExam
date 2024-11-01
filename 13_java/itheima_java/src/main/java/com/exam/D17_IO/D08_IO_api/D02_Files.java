package com.exam.D17_IO.D08_IO_api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JDK自带的工具类
 */
public class D02_Files {
    public static void main(String[] args) throws IOException {
        Files.delete(Path.of("GBK1.txt"));
        Files.copy(Path.of("GBK.txt"), Path.of("GBK1.txt"));

        System.out.println(Files.readString(Path.of("GBK1.txt"), Charset.forName("GBK")));
    }
}
