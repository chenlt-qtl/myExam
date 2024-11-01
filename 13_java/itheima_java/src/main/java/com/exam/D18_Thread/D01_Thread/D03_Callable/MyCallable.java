package com.exam.D18_Thread.D01_Thread.D03_Callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {

    private int count;

    public MyCallable(int count) {
        this.count = count;
    }

    @Override
    public String call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= count; i++) {
            sum += i;
            System.out.println("子线程计算了" + i + "次");
        }
        return "执行完了，1-" + count + "的和是" + sum;
    }
}
