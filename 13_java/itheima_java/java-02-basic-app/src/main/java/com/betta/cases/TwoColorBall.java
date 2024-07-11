package com.betta.cases;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 投注号码由6个红色球号码和1个蓝色球号码组成。
 * 红色球号码从1-33中选择,不能重复;
 * 蓝色球号码从1-16中选择，
 * 一等奖 6+1  1000W
 * 二等奖 6+0  500W
 * 三等奖 5+1  3000元
 * 四等奖 5+0 或 4+1  200元
 * 五等奖 4+0 3+1     10元
 * 六等奖 2+1 1+1 0+1 5元
 */
public class TwoColorBall {
    public static void main(String[] args) {
        int[] luckNum = createLuckNum();//摇号

        int[] userNum = userSelectNum();//选号

        judge(userNum, luckNum);//开奖

    }

    /**
     * 用户投注一组号码，返回前六个数是红球，最后一个数是蓝球
     *
     * @return
     */
    public static int[] userSelectNum() {
        Scanner scanner = new Scanner(System.in);
        int[] result = new int[7];
        //红球
        for (int i = 0; i < result.length - 1; i++) {
            System.out.println("请输入要购买的第" + (i + 1) + "个红球号码（1-33之间,不能重复）:");
            while (true) {
                int num = scanner.nextInt();
                if (num < 1 || num > 33) {
                    System.out.println("号码范围在1-33之间，请重新输入");
                } else {
                    if (checkInclude(result, num)) {
                        System.out.println("号码不能重复，请重新输入");
                    } else {
                        result[i] = num;
                        break;
                    }
                }
            }
        }

        //蓝球
        System.out.println("请输入要购买的蓝球号码（1-16）：");
        while (true) {
            int num = scanner.nextInt();
            if (num < 1 || num > 16) {
                System.out.println("号码范围在1-16之间，请重新输入");
            } else {
                result[result.length - 1] = num;
                break;
            }
        }

        System.out.print("你购买的号码是：");
        printArr(result);
        return result;
    }

    /**
     * 打印数组
     *
     * @param arr
     */
    public static void printArr(int[] arr) {
        System.out.print("[");
        Arrays.stream(arr).forEach(i -> System.out.print(i + " "));
        System.out.println("]");
    }

    /**
     * 检查数组是否包含该数
     *
     * @param arr
     * @param num
     * @return
     */
    public static boolean checkInclude(int[] arr, int num) {
        return Arrays.stream(arr).anyMatch(j -> j == num);
    }

    /**
     * 随机生成中奖号码
     *
     * @return
     */
    public static int[] createLuckNum() {
        int[] result = new int[7];
        Random random = new Random();
        //红
        for (int i = 0; i < result.length - 1; i++) {
            while (true) {
                int num = random.nextInt(33) + 1;
                if (!checkInclude(result, num)) {
                    result[i] = num;
                    break;
                }
            }
        }
        //蓝
        result[6] = random.nextInt(16) + 1;
        System.out.println("幸运号码是:");
        printArr(result);
        return result;
    }

    public static void judge(int[] userSelectNum, int[] luckNum) {
        int redCount = 0;
        int blueCount = 0;
        //红球
        for (int i = 0; i < userSelectNum.length - 1; i++) {
            for (int j = 0; j < luckNum.length - 1; j++) {
                if (userSelectNum[i] == luckNum[j]) {
                    redCount++;
                    break;
                }
            }
        }

        //蓝球
        blueCount = userSelectNum[6] == luckNum[6] ? 1 : 0;

        System.out.println("红球命中" + redCount + "个");
        System.out.println("蓝球命中" + blueCount + "个");

        if(redCount==6&&blueCount==1){
            System.out.println("一等奖 1000W");
        }else if(redCount==6&&blueCount==0){
            System.out.println("二等奖 500W");
        }else if(redCount==5&&blueCount==1){
            System.out.println("三等奖 3000");
        }else if(redCount==4&&blueCount==0||redCount==3&&blueCount==1){
            System.out.println("四等奖 200");
        }else if(redCount==4&&blueCount==0||redCount==3&&blueCount==1){
            System.out.println("五等奖 10");
        }else if(redCount==2&&blueCount==1||redCount==1&&blueCount==1||redCount==0&&blueCount==1){
            System.out.println("六等奖 5");
        }
    }
}
