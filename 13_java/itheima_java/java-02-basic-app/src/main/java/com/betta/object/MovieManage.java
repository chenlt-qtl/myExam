package com.betta.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MovieManage {
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

class Movie {
    private int id;
    private String name;
    private double price;

    public Movie() {
    }

    public Movie(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class MovieOperator {

    private List<Movie> movies = new ArrayList<>();

    public MovieOperator addMovie(Movie movie) {
        movies.add(movie);
        return this;
    }

    public void privateAllMovies() {
        movies.forEach(m -> {
            System.out.println("编号：" + m.getId());
            System.out.println("名称：" + m.getName());
            System.out.println("价格：" + m.getPrice());
            System.out.println("----------------");
        });
    }

    public void getById(int id) {
        List<Movie> collect = movies.stream().filter(m -> m.getId() == id).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Movie movie = collect.get(0);
            System.out.println("找到电影:" + movie.getName() + " ,价格是:" + movie.getPrice());
        } else {
            System.out.println("没找到对应信息");
        }
    }
}