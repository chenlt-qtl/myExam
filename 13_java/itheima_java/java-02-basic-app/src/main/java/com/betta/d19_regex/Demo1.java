package com.betta.d19_regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo1 {
    public static void main(String[] args) {
        //校验QQ号码是否正确，要求全部是数字，长度是6-20之间，不能以0开头
        //方法一
        String qq = "1113127542";
        System.out.println(qq.matches("[1-9]\\d{5,19}"));

        //方法二
        Pattern p = Pattern.compile("[1-9]\\d{5,19}");
        Matcher matcher = p.matcher(qq);
        System.out.println(matcher.matches());

        //方法三
        System.out.println(Pattern.matches("[1-9]\\d{5,19}", qq));

        //忽略大小写
        System.out.println(Pattern.matches("a((?i)b)c", "aBc"));//true
        System.out.println(Pattern.matches("a(?i)bc", "aBC"));//true

        System.out.println("------------------------------");

    }
}
