package com.exam.D18_Thread.D03_Communication;

public class Eater extends Thread{
    private Desk desk;
    public Eater(Desk desk,String name){
        super(name);
        this.desk = desk;
    }
    @Override
    public void run() {
        while (true) {
            desk.getBag();
        }
    }
}
