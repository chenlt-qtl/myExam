package com.exam.D04_FlowControl;

public class D03_Loop {
    public static void main(String[] args) {
        //需求:世界最高山峰珠穆朗玛峰高度是:8848.86米=8848860毫米，假如我有一张足够大的纸，
        // 它的厚度是0.1毫米。请问:该纸张折叠多少次，可以折成珠穆朗玛峰的高度?

        {
            int targetHeight = 8848860;
            Float myHeight = 0.1F;
            int time = 0;

            while (myHeight < targetHeight) {
                myHeight *= 2;
                time++;
            }

            System.out.println(String.format("该纸张折叠%d次，可以折成珠穆朗玛峰的高度", time));
            System.out.println(String.format("纸张是厚度是%f", myHeight));
        }

        //用for实现
        {
            int targetHeight = 8848860;
            Float myHeight = 0.1F;
            int time = 0;

            for (;myHeight < targetHeight;time++) {
                myHeight *= 2;
            }

            System.out.println(String.format("该纸张折叠%d次，可以折成珠穆朗玛峰的高度", time));
            System.out.println(String.format("纸张是厚度是%f", myHeight));
        }
    }
}
