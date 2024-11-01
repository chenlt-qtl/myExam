package com.exam.D08_Object;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieOperator {

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
