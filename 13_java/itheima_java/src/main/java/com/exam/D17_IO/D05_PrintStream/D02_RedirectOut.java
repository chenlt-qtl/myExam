package com.exam.D17_IO.D05_PrintStream;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * 重定向sout到文件中
 */
public class D02_RedirectOut {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("好好学习");
        try (PrintStream ps = new PrintStream("d27_redirect.txt")) {
            System.setOut(ps);
            System.out.println("天天向上");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
