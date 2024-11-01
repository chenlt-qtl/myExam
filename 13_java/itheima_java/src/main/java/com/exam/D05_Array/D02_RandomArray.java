package com.exam.D05_Array;

import java.util.Random;

public class D02_RandomArray {
    public static void main(String[] args) {
        //输入5名员工的编号后，打乱顺序输出
        int[] codes = {3, 45, 65, 98, 2};
        Random random = new Random();
//        Scanner scanner = new Scanner(System.in);
//        for (int i = 0; i < codes.length; i++) {
//            System.out.println("请输入第"+(i+1)+"位员工的编号:");
//            codes[i] = scanner.nextInt();
//        }

        System.out.println("排序前:");
        for (int i = 0; i < codes.length; i++) {
            System.out.print(codes[i] + " ");
        }

        for (int i = 0; i < codes.length; i++) {
            int j = random.nextInt(codes.length);
            int temp = codes[j];
            codes[j] = codes[i];
            codes[i] = temp;
        }
        System.out.println("--------------------");
        System.out.println("排序后:");
        for (int i = 0; i < codes.length; i++) {
            System.out.print(codes[i] + " ");
        }
    }
}
