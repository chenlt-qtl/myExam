package com.exam.D18_Thread.D01_Thread;

public class D06_SleepJoin {
    public static void main(String[] args) throws InterruptedException {


        Thread mainThread = Thread.currentThread();
        mainThread.setName("王五");
        String name = mainThread.getName();
        for (int i = 1; i <= 5; i++) {
            if(i==3){
                //让当前执行的线程暂停5秒，再继续执行
                Thread.sleep(5000);
            }
            System.out.println(name + "线程输出：" + i);
        }

        //join方法会让当前的线程先执行完
        Thread t1 = new MyThread("张三");
        Thread t2 = new MyThread("李四");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }
}
