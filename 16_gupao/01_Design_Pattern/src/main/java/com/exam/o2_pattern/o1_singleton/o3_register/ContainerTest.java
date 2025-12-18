package com.exam.o2_pattern.o1_singleton.o3_register;

public class ContainerTest {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ExectorThread());
        Thread thread2 = new Thread(new ExectorThread());
        Thread thread3 = new Thread(new ExectorThread());
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class Pojo{

}
