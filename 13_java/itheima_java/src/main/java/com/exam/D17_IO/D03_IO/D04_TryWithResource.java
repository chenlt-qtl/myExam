package com.exam.D17_IO.D03_IO;

import java.io.IOException;

public class D04_TryWithResource {
    public static void main(String[] args) throws IOException {

        try (
                MyClass myClass = new MyClass();
        ) {
            System.out.println("执行任务");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class MyClass implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("关闭资源");
    }
}
