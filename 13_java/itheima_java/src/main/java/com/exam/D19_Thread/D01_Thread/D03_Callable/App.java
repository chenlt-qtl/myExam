package com.exam.D19_Thread.D01_Thread.D03_Callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new MyCallable(100);
        //封装成一个futureTask对象
        //futureTask实现了Runnable
        FutureTask<String> future = new FutureTask(callable);
        //执行线程
        new Thread(future).start();
        //获取执行结果
        String s = future.get();
        System.out.println(s);

    }
}

