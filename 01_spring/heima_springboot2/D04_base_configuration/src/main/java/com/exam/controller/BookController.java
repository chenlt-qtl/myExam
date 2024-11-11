package com.exam.controller;

import com.exam.MyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Value("${country}")
    private String data;

    @Value("${users[1].age}")
    private String age;

    @Value("${tempDir}")
    private String dir;

    @Value("${tempDir1}")
    private String dir1;

    @Autowired
    private Environment env;

    @Autowired
    private MyDataSource myDataSource;

    @GetMapping
    public String getById(){
        System.out.println("springboot is running ");
        System.out.println("country: " + data);
        System.out.println("age: " + age);
        System.out.println("dir: " + dir);
        System.out.println("dir1: " + dir1);
        System.out.println("-----------------");
        System.out.println("age:"+env.getProperty("users[1].age"));
        System.out.println("=====================");
        System.out.println("datasource:"+myDataSource);
        return "springboot is running...";
    }
}
