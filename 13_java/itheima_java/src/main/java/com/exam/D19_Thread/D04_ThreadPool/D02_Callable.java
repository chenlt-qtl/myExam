package com.exam.D19_Thread.D04_ThreadPool;

import java.util.concurrent.*;

public class D02_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 8, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

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
