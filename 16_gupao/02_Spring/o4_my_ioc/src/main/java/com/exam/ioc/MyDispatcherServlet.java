package com.exam.ioc;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MyDispatcherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ApplicationContext context = new MyApplicationContext("applicationContext.xml");

    }
}
