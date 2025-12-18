package com.betta.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id){
        return "张三，18岁";
    }

}
