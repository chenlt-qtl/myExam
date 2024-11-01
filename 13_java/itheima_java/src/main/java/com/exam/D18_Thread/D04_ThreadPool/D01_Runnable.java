package com.exam.D18_Thread.D04_ThreadPool;

import java.util.concurrent.*;

public class D01_Runnable {

    public static void main(String[] args) {


        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 8, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        Runnable myRunnable = new MyRunnable();

        pool.execute(myRunnable);
        pool.execute(myRunnable);
        pool.execute(myRunnable);
        //任务队列
        pool.execute(myRunnable);
        pool.execute(myRunnable);
        pool.execute(myRunnable);
        pool.execute(myRunnable);
        //到了创建临时线程的创建时机
        pool.execute(myRunnable);
        pool.execute(myRunnable);

        //到了拒绝任务的时机
        pool.execute(myRunnable);

        pool.shutdown();//等任务处理完关闭线程池
//        pool.shutdownNow();//立即关闭线程池
    }
}
