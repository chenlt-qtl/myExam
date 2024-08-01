package com.betta.d18_algorithm;

import java.util.Arrays;


public class Demo1 {
    public static void main(String[] args) {


        //冒泡排序
        System.out.println("------------冒泡排序-----------");
        int[] a = {5, 2, 3, 1};
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(a));

        //选择排序
        System.out.println("------------选择排序-----------");
        int[] b = {5, 2, 3, 1};

        for (int i = 0; i < b.length - 1; i++) {
            for (int j = i + 1; j < b.length; j++) {
                if (b[i] > b[j]) {
                    int tmp = b[j];
                    b[j] = b[i];
                    b[i] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(b));

        //选择排序
        System.out.println("------------选择排序(优化)-----------");
        int[] c = {5, 2, 3, 1};
        int minIdx;

        for (int i = 0; i < c.length - 1; i++) {
            minIdx = i;
            //找到后面的最小值
            for (int j = i + 1; j < c.length; j++) {
                if (c[j] < c[minIdx]) {
                    minIdx = j;
                }
            }
            //与最小值比较，并决定是否交换
            if (i != minIdx) {
                int tmp = c[i];
                c[i] = c[minIdx];
                c[minIdx] = tmp;
            }
        }
        System.out.println(Arrays.toString(c));
    }
}
