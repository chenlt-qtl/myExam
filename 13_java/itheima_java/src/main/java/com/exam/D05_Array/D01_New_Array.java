package com.exam.D05_Array;

import java.util.Arrays;

public class D01_New_Array {
    public static void main(String[] args) {
        //标准写法
        int[] a = new int[]{1,2,3};
        int d[] = new int[]{1,2,3};

        //简化写法
        int[] b = {1,2,3};
        String[] c = {"a","b","c"};
        System.out.println(a);
        System.out.println(c);
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i]);
        }

        //动态初始化
        int[] e = new int[3];
        System.out.println(Arrays.toString(e));//[0,0,0]
    }
}
