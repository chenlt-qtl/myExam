package com.betta.array;

public class Demo1 {
    public static void main(String[] args) {
        int[] a = new int[]{1,2,3};
        int d[] = new int[]{1,2,3};
        int[] b = {1,2,3};
        String[] c = {"a","b","c"};
        System.out.println(a);
        System.out.println(c);
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i]);
        }

        int[] e = new int[3];
    }
}
