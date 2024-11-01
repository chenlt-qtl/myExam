package com.exam.D18_Thread.D05_OptimisticLock;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * 有100分礼品，小红和小明两人同时发送，当剩下礼品小于10份的时候不再送出，
 * 利用多线程模拟该过程并将线程的名称打印出来，并最后在控制台分别打印小红和小明各自送出多少份礼物
 */
public class D02_Gift {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<String> gifts = new ArrayList<>();
        Random random = new Random();
        String[] str = new String[]{"口红", "奥特曼", "公仔", "积木", "保温杯", "音箱"};
        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(str.length);
            String gift = str[index];
            gifts.add((i + 1) + "号" + gift);
        }
        System.out.println(gifts);



        MyThread thread = new MyThread("小明",gifts);
        thread.start();
        MyThread thread1 = new MyThread("小红",gifts);
        thread1.start();

        thread.join();
        thread1.join();


        System.out.println(thread.getCount());
        System.out.println(thread1.getCount());

    }
}

class MyThread extends Thread {

    private List<String> gifts;
    private int count;

    public MyThread(String name,List<String> gifts) {
        super(name);
        this.gifts = gifts;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        Random random = new Random();
        String name = Thread.currentThread().getName();
        while (true) {
            String remove;
            synchronized (gifts) {
                if (gifts.size() < 10) {
                    break;
                }
                remove = gifts.remove(random.nextInt(gifts.size()));
            }
            System.out.println(name + "送出了" + remove);
            count++;
        }
    }

}
