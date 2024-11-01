package com.exam.D17_IO.D01_File;

public class D02_RecursionDemo {

    public static void main(String[] args) {
        //计算n的阶乘，5的阶乘=1*2*3*4*5 6的阶乘=1*2*3*4*5*6
        System.out.println(1*2*3*4*5);
        System.out.println(getResult(5));

        System.out.println(1*2*3*4*5*6);
        System.out.println(getResult(6));

        //计算1-n的和
        System.out.println(1+2+3+4+5);
        System.out.println(getResult1(5));

        System.out.println(1+2+3+4+5+6);
        System.out.println(getResult1(6));
    }

    private static int getResult1(int i) {
        if(i<=1){
            return 1;
        }else {
            return i+getResult1(i-1);
        }

    }

    public static int getResult(int i){
        if(i>1){
            return i*getResult(i-1);
        }
        return 1;
    }
}
