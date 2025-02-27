package com.exam.D09_API;

import java.io.IOException;

public class D03_Runtime {
    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("处理器核心数：" + runtime.availableProcessors());
        System.out.println(runtime.totalMemory());
        System.out.println(runtime.freeMemory());

        Process exec = runtime.exec("D:\\PF\\QQ\\Bin\\QQLiveMPlayer\\QQLivePlayer.exe");
        Thread.sleep(5000);
        exec.destroy();
    }
}
