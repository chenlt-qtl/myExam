package com.exam.D17_IO.D02_Charset;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class D01_ChartSet {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String string = "你好吗 abc";
        byte[] bytes = string.getBytes("UTF-8");
        System.out.println(Arrays.toString(bytes));

        String newStr = new String(bytes,"GBK");
        System.out.println(newStr);

        newStr = new String(bytes,"UTF-8");
        System.out.println(newStr);
    }
}
