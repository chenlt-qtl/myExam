package com.exam.D17_IO.D01_File;

/**
 * 猴子吃桃问题
 * 猴子第一天摘下若干桃子，当即吃了一半，觉得好不过瘾，于是又多吃了一个
 * 第二天又吃了前天剩余桃子数量的一半，觉得好不过瘾，于是又多吃了一个
 * 以后每天都是吃前天剩余桃子数量的一半，觉得好不过瘾，又多吃了一个
 * 等到第10天的时候发现桃子只有1个了。
 * 需求:请问猴子第一天摘了多少个桃子?
 * f(n)=当天桃子数
 * 10  f(10)=1
 * 9   (f(10)+1)*2 = f(10)
 * 8 (f(9)+1)*2 = f(8)
 * <p>
 * 2 (f(3)+1)*2 = f(2)
 * 1 (f(2)+1)*2 = f(1)
 * 公式：(f(n+1)+1)*2=f(n)
 */

public class D03_Monkey {
    public static void main(String[] args) {
        System.out.println(rest(1));
    }

    public static int rest(int day) {
        if (day >= 10) {
            return 1;
        } else {
            return (rest(day + 1) + 1) * 2;
        }
    }
}
