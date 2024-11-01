package com.exam.D17_IO.D01_File;

import java.io.File;
import java.io.IOException;

public class D05_FileSearchAndDelete {
    public static void main(String[] args) throws IOException {
        File file = new File("test444");
        if (!file.exists()) {
            file.mkdir();
        }

        File file4 = new File(file, "4");
        File file5 = new File(file, "5");
        File file6 = new File(file5, "6");
        file4.mkdir();
        file6.mkdirs();
        File file1 = new File(file4, "123.txt");
        File file2 = new File(file5, "234.txt");
        File file3 = new File(file6, "345.txt");
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();
        search(file, "4");

        delete(file);//递归删除
        System.out.println("删除完成");
    }

    private static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File file1 : files) {
                        delete(file1);//递归删除
                    }
                }
            }
            file.delete();
        }
    }

    public static void search(File file, String str) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File file1 : files) {
                        search(file1, str);
                    }
                }
            } else {
                if (file.getName().contains(str)) {
                    System.out.println("找到文件：" + file.getAbsolutePath());
                }
            }
        }
    }
}
