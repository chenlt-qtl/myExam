package com.exam.D19_Thread.D03_Communication;

import java.util.ArrayList;
import java.util.List;

public class Desk {
    List<String> bags = new ArrayList<>();

    public synchronized void getBag() {
        String name = Thread.currentThread().getName();
        if(!bags.isEmpty()){
            System.out.println(name+"吃了"+bags.get(0));
            bags.clear();
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //等待自己 唤醒别人
            this.notifyAll();
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void putBag() {

        String name = Thread.currentThread().getName();
        if(bags.isEmpty()){
            bags.add(name+"做的肉包子");
            System.out.println(name+"做了一个肉包子");
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            //等待自己 唤醒别人
            this.notifyAll();
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
