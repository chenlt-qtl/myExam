package com.betta.cases;

/**
 * *
 * ***
 * *****
 * *******
 * <p>
 * 空格  *
 * 1     3   1
 * 2     2   3
 * 3     1   5
 * 4     0   7
 */

public class Star {
    public static void main(String[] args) {
        printStar(10);
    }

    public static void printStar(int line) {
        for (int i = 1; i <= line; i++) {
            //1. 打印空格
            System.out.print(" ".repeat(line-i));
            //2. 打印*
            System.out.print("*".repeat((i-1)*2+1));
            //3. 换行
            System.out.println();
        }
    }
}
