package com.exam.D17_IO.D08_IO_api;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class D01_CommonIo {
    public static void main(String[] args) throws Exception {
        FileUtils.copyFile(new File("GBK.txt"), new File("GBK1.txt"));
    }
}
