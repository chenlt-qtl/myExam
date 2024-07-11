package com.betta.flowControl;

import java.util.Random;
import java.util.Scanner;

/**
 * 案例 生成随机数
 */
public class RandomDemo {

    public static void main(String[] args) {
        Random r = new Random();

//        for (int i = 0; i < 10; i++) {
//            //生成3-17随机数
//            System.out.println(r.nextInt(15)+3);
//        }
        System.out.println("-------------------");
        Scanner sc = new Scanner(System.in);
        int i = r.nextInt(100) + 1;
        while (true){
            System.out.println("请输入猜测的数据");
            int num = sc.nextInt();
            if(num>i){
                System.out.println("高了");
            }else if(num<i){
                System.out.println("低了");
            }else {
                System.out.println("猜中了，答案是"+i);
                break;
            }
        }
    }
}
