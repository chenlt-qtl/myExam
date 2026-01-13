package com.exam.D19_Thread.D03_Communication;

public class Cooker extends Thread{
    private Desk desk;

    public Cooker(Desk desk,String name){
        super(name);
        this.desk = desk;
    }
    @Override
    public void run() {
        while (true) {
            desk.putBag();
        }
    }
}
