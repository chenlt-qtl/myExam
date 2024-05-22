package com.seed.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
@Slf4j
public class SessionController {
    @PostMapping
    public String setToSession(HttpSession session){
        log.info("HttpSession-post:{}",session.hashCode());
        session.setAttribute("abc","345");
        return "success";
    }

    @GetMapping
    public String getFromSession(HttpServletRequest request){
        String value = "";
        HttpSession session = request.getSession();
        log.info("HttpSession-get:{}",session.hashCode());
        Object abc = session.getAttribute("abc");
        return abc.toString();
    }
}
