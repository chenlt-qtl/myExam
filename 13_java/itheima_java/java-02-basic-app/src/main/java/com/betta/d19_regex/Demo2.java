package com.betta.d19_regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo2 {
    public static void main(String[] args) {
        //提取内容
        Pattern p1 = Pattern.compile("(\\W+)\\w+");
        Matcher matcher1 = p1.matcher("古力娜扎2123迪丽热巴qwewqe121热依扎wer32rf赵丽颖11666");//把名字提取出来
        while (matcher1.find()){
            System.out.println(matcher1.group(1));
        }
        System.out.println(matcher1.matches());

        //替换内容
        String str = "我我我爱编编程程程"; //转成 我爱编程
        System.out.println(str.replaceAll("(.)\\1+", "$1"));

        //分割内容
        String[] split = "古力娜扎2123迪丽热巴qwewqe121热依扎wer32rf赵丽颖11666".split("\\w+");
        System.out.println(Arrays.toString(split));
    }
}
