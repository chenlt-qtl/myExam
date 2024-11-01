package com.exam.D20_Java_adva.D01_Unittest;

/**
 * 被测试类
 */
public class MyUtils {

    public int add(int a, int b) {
        return a + b;
    }
    public Double division(double a,double b ){
        if(b == 0){
            System.out.println("除数不能为O");
            return 0.0;
        }else {
            return a/b;
        }
    }
}
