package com.exam.D17_IO.D04_Buffer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 将乱序的古朗月行还原
 */
public class D05_GuLangYueXing {
    public static void main(String[] args) {
        try(
                Reader re = new FileReader("GuLangYueXing.txt");
                BufferedReader br = new BufferedReader(re);
                Writer wt = new FileWriter("GuLangYueXing1.txt");
                BufferedWriter bw = new BufferedWriter(wt);
                ){
            List<String> list = new ArrayList<>();
            String str;
            while ((str=br.readLine())!=null){
                list.add(str);
            }

            //排序
            Collections.sort(list);

            for (String s : list) {
                bw.write(s);
                bw.newLine();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
