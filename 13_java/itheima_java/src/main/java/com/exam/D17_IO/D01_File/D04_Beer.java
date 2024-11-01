package com.exam.D17_IO.D01_File;

public class D04_Beer {

    private static int total = 0;//总瓶数
    private static int bottle = 0;//空瓶子
    private static int cap = 0;//瓶盖

    public static void main(String[] args) {
        //啤酒问题:啤酒2元一瓶，4个盖子可以换一瓶，2个空瓶可以换一瓶，请问10元可以喝多少瓶?
        /**  瓶   盖   空瓶
         * 2 1
         * 4 2          1
         * 6 3    +1     1
         * 8 11    3     1
         * 10 15    3     1
         *
         */

        buy(10);
        System.out.println(total);
    }

    private static void buy(int money) {
        if(money<=1){
            return ;
        }
        int num = money/2;
        int restMoney;
        total += num;
        bottle += num;
        cap += num;
        restMoney=(bottle/2+cap/4)*2;
        bottle=bottle%2;
        cap=cap%4;
        buy(restMoney);
    }
}
