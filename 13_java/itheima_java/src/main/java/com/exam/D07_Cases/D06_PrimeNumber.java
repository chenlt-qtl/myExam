package com.exam.D07_Cases;

/**
 * 判断 101-200 之间有多少个素数，并输出所有素数
 * 说明:除了1和它本身以外，不能被其他正整数整除，就叫素数。
 * 比如:3、7就是素数，而9、21等等不是素数。
 */
public class D06_PrimeNumber {
    public static void main(String[] args) {
        System.out.println("找到" + getPrimeNumber1(101, 200) + "个素数");
        System.out.println("找到" + getPrimeNumber2(101, 200) + "个素数");
    }

    private static int getPrimeNumber2(int min, int max) {
        int result = 0;
        //为外部循环指定标签
        for (int i = min; i <= max; i++) {
            if (check(i)) {
                System.out.println("找到素数" + i);
                result++;
            }

        }
        return result;
    }

    public static int getPrimeNumber1(int min, int max) {
        int result = 0;
        OUT:
        //为外部循环指定标签
        for (int i = min; i <= max; i++) {
            for (int j = 2; j <= (i / 2); j++) {
                if (i % j == 0) {
                    continue OUT;//继续OUT的循环
                }
            }

            System.out.println("找到素数" + i);
            result++;

        }
        return result;

    }

    /**
     * 检查是不是素数
     *
     * @param i
     * @return
     */
    public static boolean check(int i) {
        for (int j = 2; j <= (i / 2); j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }
}
