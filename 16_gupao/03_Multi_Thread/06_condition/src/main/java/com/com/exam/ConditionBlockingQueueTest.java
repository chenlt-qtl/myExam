package com.com.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBlockingQueueTest {

    //表示阻塞队列中的容器
    private List<String> items;

    //数组的容量
    private volatile int size;

    //元素个数
    private volatile int count;

    private Lock lock = new ReentrantLock();

    //让take方法阻塞
    private final Condition isEmpty = lock.newCondition();

    //让add方法阻塞
    private final Condition isFull = lock.newCondition();

    public ConditionBlockingQueueTest(int size) {
        this.size = size;
        items = new ArrayList<String>(size);
    }

    //添加一个元素，
    public void add(String item) throws InterruptedException {
        lock.lock();
        try{
            //队列满了，需要等待
            if(count >= size){
                System.out.println("队满了，先等一会儿");
                isFull.await();
            }
            ++count;
            items.add(item);
            isEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    //添加一个元素，
    public String take() throws InterruptedException {
        lock.lock();
        try{
            //队列空了，需要等待
            if(count == 0){
                System.out.println("队空了，先等一会儿");
                isEmpty.await();
            }
            --count;
            String item = items.remove(0);
            isFull.signal();
            return item;
        }finally {
            lock.unlock();
        }
    }
}
