package com.exam.D15_Collections;

import java.util.Arrays;

/**
 * 可变参数
 */
public class D05_variable_parameter {
    public static void main(String[] args) {
        test();
        test(1);
        test(1,2,3,4);
    }
    public static void test(int...value){
        System.out.println(Arrays.toString(value));
    }
}
