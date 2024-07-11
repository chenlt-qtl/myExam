package com.betta.array;

import java.util.Scanner;

/**
 * 歌唱比赛
 */
public class SingingContest {
    public static void main(String[] args) {
        /*
        需求
        某歌唱比赛，需要开发一个系统:可以录入6名评委的打分，录入完毕后立即输出平均分做为选手得分
                分析
        1. 6名评委的打分是后期录入的，一开始不知道具体的分数，因此定义一个动态初始化的数组存分数。
        2. 遍历数组中的每个位置，每次提示用户录入一个评委的分数，并存入到数组对应的位置。
        3. 遍历数组中的每个元素进行求和，最终算出平均分打印出来即可。
         */
        int[] score = new int[6];
        int total = 0;
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < score.length; i++) {
            System.out.println("请评委" + (i + 1) + "输入分数(1-10):");
            int nextInt = scanner.nextInt();
            score[i] = nextInt;
        }

        for (int i = 0; i < score.length; i++) {
            total += score[i];
        }
        System.out.println("最后得分: " + (total / score.length * 1.0));
    }
}
