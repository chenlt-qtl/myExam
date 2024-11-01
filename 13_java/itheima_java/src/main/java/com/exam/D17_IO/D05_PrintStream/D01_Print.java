package com.exam.D17_IO.D05_PrintStream;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class D01_Print {
    public static void main(String[] args) {
        try (
                PrintStream ps = new PrintStream("d27_print.txt", Charset.forName("GBK"));

                PrintWriter pw = new PrintWriter("d27_print1.txt", Charset.forName("GBK"));

        ) {
            ps.println(97);
            ps.println("你好");
            ps.println(true);
            ps.write(97);
            pw.write("你好啊");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
