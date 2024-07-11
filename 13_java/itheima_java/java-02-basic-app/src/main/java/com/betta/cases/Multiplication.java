package com.betta.cases;

/**
 * 打印99乘法表
 */
public class Multiplication {
    public static void main(String[] args) {
        printMult();
    }

    public static void printMult() {
        //打印9行
        for (int i = 1; i <= 9; i++) {
            printMultLine(i);
            System.out.println();
        }
    }

    /**
     * 打印单行
     * @param i
     */
    private static void printMultLine(int i) {
        for (int j = 1; j <= i; j++) {
            System.out.print(j + "X" + i + "=" + j * i + "\t");
        }
    }
}
