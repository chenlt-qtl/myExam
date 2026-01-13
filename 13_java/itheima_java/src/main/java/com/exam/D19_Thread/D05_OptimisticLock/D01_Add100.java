package com.exam.D19_Thread.D05_OptimisticLock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁 悲观锁例子 100个线程 每个线程对数据加100次
 */
public class D01_Add100 {

    public static final int TIME = 1000;

    public static void main(String[] args) throws InterruptedException {
        Pessimistic pessimistic = new Pessimistic();//悲观
        Optimistic optimistic = new Optimistic();//乐观
        NoLock noLock = new NoLock();//无锁

        Thread thread1 = null;
        Thread thread2 = null;
        Thread thread3 = null;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < TIME; i++) {
            thread1 = new Thread(noLock);
            thread1.start();
        }

        thread1.join();
        System.out.println("无锁运行结果:" + noLock.getCount());

        System.out.println("无锁运行时间" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < TIME; i++) {
            thread2 = new Thread(pessimistic);
            thread2.start();
        }
        thread2.join();
        System.out.println("悲观锁运行结果:" + pessimistic.getCount());

        System.out.println("悲观锁运行时间" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");

        startTime = System.currentTimeMillis();
        for (int i = 0; i < TIME; i++) {
            thread3 = new Thread(optimistic);
            thread3.start();
        }
        thread3.join();
        System.out.println("乐观锁运行结果:" + optimistic.getCount());

        System.out.println("乐观锁运行时间" + (System.currentTimeMillis() - startTime) / 1000.0 + "秒");

    }
}


/**
 * 无锁
 */
class NoLock implements Runnable {

    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < D01_Add100.TIME; i++) {
            ++count;
        }
    }
}


/**
 * 乐观锁
 */
class Optimistic implements Runnable {


    //整数修改的乐观锁，原子类实现
    private AtomicInteger count = new AtomicInteger();

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    @Override
    public void run() {

        for (int i = 0; i < D01_Add100.TIME; i++) {
            count.incrementAndGet();
        }
    }
}

/**
 * 悲观锁
 */
class Pessimistic implements Runnable {
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < D01_Add100.TIME; i++) {
                ++count;
            }
        }
    }
}