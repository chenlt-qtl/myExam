package com.exam.D17_IO.D01_File;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D01_FileDemo {
    public static void main(String[] args) throws IOException {

        File file = new File("test333");

        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println(file.mkdir());
        }
        File file1 = new File("test333/1.txt");
        File file2 = new File("test333/2.txt");
        File file3 = new File("test333/3.txt");
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();

        //重命名文件(每个文件名加上10)
        for (File fileObj : file.listFiles()) {
            String name = fileObj.getName();
            System.out.println(name);
            Pattern pattern = Pattern.compile("^(\\d)+(\\.[\\W\\w]+)");
            Matcher matcher = pattern.matcher(name);
            while (matcher.find()){
                int i = Integer.parseInt(matcher.group(1)) + 10;
                fileObj.renameTo(new File(file,i+matcher.group(2)));
            }

        }
        System.out.println("改名后");
        for (File fileObj : file.listFiles()) {
            System.out.println(fileObj.getName());
            //删除文件
            fileObj.delete();
        }
        file.delete();
    }
}
