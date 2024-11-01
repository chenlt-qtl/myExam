package com.exam.D21_exam.d04_money;

import java.util.*;

/**
 * 。红包雨游戏，某企业有100名员工，员工的工号依次是1,2，3,4，.到100。
 * 现在公司举办了年会活动，活动中有一个红包雨环节，要求共计发出200个红包雨。
 * 其中小红包在[1.30]元之间，总占比为80%，大红包[31-100]元，总占比为20%。
 * 具体的功能点如下
 * 1、系统模拟上述要求产生200个红包。
 * 2、模拟100个员工抢红包雨，需要输出哪个员工抢到哪个红包的过程，活动结束时需要提示活动结束.
 * 3、活动结束后，请1对100名员工按照所抢红包的总金额进行降序排序展示，**例如:3号员工抢红包总计:293元、1号员工抢红包总计250元，...*
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> moneys = new ArrayList();
        List<PeopleThread> threads = new ArrayList<>();
        //1、系统模拟上述要求产生200个红包。
        Random random = new Random();
        //小红包
        for (int i = 0; i < 160; i++) {
            moneys.add(random.nextInt(30) + 1);
        }

        //大红包
        for (int i = 0; i < 40; i++) {
            moneys.add(random.nextInt(70) + 31);
        }

        Collections.reverse(moneys);
        System.out.println(moneys);
        System.out.println(moneys.stream().reduce(0, (t, c) -> t + c));
        //2、模拟100个员工抢红包雨，需要输出哪个员工抢到哪个红包的过程，活动结束时需要提示活动结束.
        for (int i = 0; i < 100; i++) {
            PeopleThread peopleThread = new PeopleThread(i + "号员工", moneys, random);
            threads.add(peopleThread);
            peopleThread.start();
        }

        for (PeopleThread thread : threads) {
            thread.join();
        }

        System.out.println("----------活动结束---------------");

        //3、活动结束后，请1对100名员工按照所抢红包的总金额进行降序排序展示，**例如:3号员工抢红包总计:293元、1号员工抢红包总计250元，...*

        threads.stream().sorted((o1, o2) -> Integer.compare(o2.getMoney(),o1.getMoney()))
                .forEach(i->System.out.println(i.getName()+"员工抢红包总计:"+i.getMoney()+"元"));

    }
}

class PeopleThread extends Thread {
    private int money;
    private List<Integer> moneys;

    private Random random;

    public PeopleThread(String name, List<Integer> moneys, Random random) {
        super(name);
        this.moneys = moneys;
        this.random = random;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (true) {
            if (moneys.size() == 0) {
                break;
            } else {

                synchronized (moneys) {
                    if (moneys.size() > 0) {
                        Integer i = random.nextInt(moneys.size());
                        int remove = moneys.remove(i.intValue());
                        System.out.println(name + "抢到" + remove + "元红包");
                        money += remove;
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}