package com.exam.D17_IO.D04_Transform;

import java.io.*;

/**
 * 文件与代码的编码不一致时，需要用转换流转换编码
 */
public class D01_Chartset {
    public static void main(String[] args) {
        try (
                InputStream reader = new FileInputStream("GBK.txt");
                InputStreamReader isr = new InputStreamReader(reader,"GBK");
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = new FileOutputStream("GBK1.txt");
                OutputStreamWriter osw = new OutputStreamWriter(os,"GBK");
                BufferedWriter bw = new BufferedWriter(osw);
        ) {
            System.out.println("========读文件=======");

            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                bw.write(str);
                bw.newLine();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
