package com.exam.D18_Thread.D04_ThreadPool;

import java.util.concurrent.*;

/**
 * 使用Executors操作线程池
 */
public class D03_Executors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorService pool = Executors.newSingleThreadExecutor();

        //使用线程处理callable任务
        Future<String> future1 = pool.submit(new MyCallable(10));
        Future<String> future2 = pool.submit(new MyCallable(20));
        Future<String> future3 = pool.submit(new MyCallable(30));
        Future<String> future4 = pool.submit(new MyCallable(40));
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        System.out.println(future4.get());
    }
}
