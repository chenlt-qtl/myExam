package com.exam.D21_exam.d03_menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 1. 请从系统菜单,txt中读取这些菜单信息，将这些菜单信息在控制台按顺序展示
 * 2. 将正确的菜单顺序，写出到一个新文件"系统菜单2.txt"中保存起来，详细格式如下
 */
public class App {

    public static void main(String[] args) {

        List<String> menus = new ArrayList<>();
        try (
                BufferedReader ps = new BufferedReader(new FileReader("file\\系统菜单.txt"));
                PrintStream bw = new PrintStream("file\\系统菜单2.txt");
        ) {
            //1. 请从系统菜单.txt中读取这些菜单信息，将这些菜单信息在控制台按顺序展示
            String str;
            while ((str = ps.readLine())!=null){
                menus.add(str);
            }
            Collections.sort(menus);
            menus.forEach(m->{
                String[] datas = m.split("-");
                System.out.println((datas[0].length()!=4?"\t":"")+datas[1]);

            });

            // 2. 将正确的菜单顺序，写出到一个新文件"系统菜单2.txt"中保存起来，详细格式如下
            for (String menu : menus) {
                bw.println(menu);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
