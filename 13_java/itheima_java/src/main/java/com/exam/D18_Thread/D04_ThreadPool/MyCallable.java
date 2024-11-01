package com.exam.D18_Thread.D04_ThreadPool;

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
        }
        return Thread.currentThread().getName() + "执行完了，1-" + count + "的和是" + sum;
    }
}