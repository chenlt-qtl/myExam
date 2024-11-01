package com.exam.D08_Object;

import java.util.Scanner;

public class D01_MovieManage {
    public static void main(String[] args) {
        Movie m1 = new Movie(1, "水门桥", 9.8);
        Movie m2 = new Movie(2, "出拳吧", 7.8);
        Movie m3 = new Movie(3, "月球陨落", 7.9);
        Movie m4 = new Movie(4, "一点就到家", 8.7);
        MovieOperator movieOperator = new MovieOperator();
        movieOperator.addMovie(m1).addMovie(m2).addMovie(m3).addMovie(m4);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==电影信息系统==");
            System.out.println("1. 查询全部电影信息");
            System.out.println("2. 根据ID查询电影信息");
            System.out.println("请输入操作命令：");
            int nextInt = scanner.nextInt();
            switch (nextInt){
                case 1:
                    movieOperator.privateAllMovies();
                    break;
                case 2:
                    System.out.println("请输入电影编号：");
                    nextInt = scanner.nextInt();
                    movieOperator.getById(nextInt);
                    break;
                default:
                    System.out.println("您输入的命令有误。");
            }
        }
    }
}

