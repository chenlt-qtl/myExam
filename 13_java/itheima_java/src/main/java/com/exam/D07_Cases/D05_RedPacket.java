package com.exam.D07_Cases;

import java.util.Random;
import java.util.Scanner;

/**
 * 一个大V直播时发起了抽奖活动，分别有:9、666、188、520、99999五个红包。
 * 请模拟粉丝来抽奖，按照先来先得，随机抽取，抽完即止，注意:一个红包只能被抽一次，先抽或后抽哪一个红包是随机的，
 */
public class D05_RedPacket {
    public static void main(String[] args) {
        getPacket1(new int[]{9, 99, 999, 9999, 99999});
        System.out.println("-----------------------------");
        getPacket2(new int[]{9, 99, 999, 9999, 99999});
    }

    public static void getPacket1(int[] redPacket) {
        //1.循环提醒粉丝抽奖
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        for (int i = 0; i < redPacket.length; i++) {
            System.out.println("输入任意字符抽奖:");
            scanner.next();

            while (true) {
                //2.随机抽一个红包
                int index = random.nextInt(redPacket.length);
                //3.如果不为0，打印并结束while
                if (redPacket[index] != 0) {
                    System.out.println("恭喜你，抽中" + redPacket[index] + "元红包");
                    redPacket[index] = 0;
                    break;
                }
            }
        }
        System.out.println("抽奖结束");


    }

    public static void getPacket2(int[] redPacket) {
        //1.打乱顺序
        Random random = new Random();
        for (int i = 0; i < redPacket.length; i++) {
            int newIndex = random.nextInt(redPacket.length);
            int temp = redPacket[i];
            redPacket[i] = redPacket[newIndex];
            redPacket[newIndex] = temp;
        }
        //2.顺序抽出红包
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < redPacket.length; i++) {
            System.out.println("输入任意字符抽奖:");
            scanner.next();
            System.out.println("恭喜你，抽中" + redPacket[i] + "元红包");
        }
        System.out.println("抽奖结束");
    }
}
