package com.exam.D17_IO.D04_Buffer;

import java.io.*;

/**
 * 原始和缓冲流性能对比
 */
public class D03_TimeTest {

    private static final String SRC_FILE = "E:\\workspace.rar";
    private static final String TARGET_FILE = "E:\\workspace1.rar";

    private static final int ARRAY_SIZE = 128;

    public static void main(String[] args) {
        copy02();
        copy03();
        copy04();
        deleteTarget();
    }

    /**
     * 原始流拷贝 太慢
     */
    private static void copy01() {
        deleteTarget();
        long startTime = System.currentTimeMillis();
        try (
                InputStream is = new FileInputStream(SRC_FILE);
                OutputStream os = new FileOutputStream(TARGET_FILE);
        ) {
            int b;
            while ((b = is.read()) != -1) {
                os.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("原始流拷贝耗时:" + (endTime - startTime) / 1000.0 + "秒");
    }

    /**
     * 原始流拷贝(字节数组)
     */
    private static void copy02() {
        deleteTarget();
        long startTime = System.currentTimeMillis();
        try (
                InputStream is = new FileInputStream(SRC_FILE);
                OutputStream os = new FileOutputStream(TARGET_FILE);
        ) {
            byte[] b = new byte[ARRAY_SIZE];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("原始流拷贝耗时:" + (endTime - startTime) / 1000.0 + "秒");
    }

    /**
     * 缓冲流拷贝
     */
    private static void copy03() {
        deleteTarget();
        long startTime = System.currentTimeMillis();
        try (
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(SRC_FILE));
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(TARGET_FILE));
        ) {
            byte[] b = new byte[ARRAY_SIZE];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("缓冲流拷贝耗时:" + (endTime - startTime) / 1000.0 + "秒");
    }

    /**
     * 原始流拷贝(字节数组) 使用8KB数组拷贝
     */
    private static void copy04() {
        deleteTarget();
        long startTime = System.currentTimeMillis();
        try (
                InputStream is = new FileInputStream(SRC_FILE);
                OutputStream os = new FileOutputStream(TARGET_FILE);
        ) {
            byte[] b = new byte[1024 * 16];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("原始流16KB数组拷贝耗时:" + (endTime - startTime) / 1000.0 + "秒");
    }

    /**
     * 删除旧文件
     */
    private static void deleteTarget() {
        File file = new File(TARGET_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
