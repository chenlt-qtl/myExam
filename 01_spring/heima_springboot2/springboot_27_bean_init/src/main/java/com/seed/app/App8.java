package com.seed.app;

import com.seed.bean.Dog;
import com.seed.bean.service.BookService;
import com.seed.bean.service.impl.BookServiceImpl1;
import com.seed.bean.service.impl.BookServiceImpl4;
import com.seed.config.SpringConfig7;
import com.seed.config.SpringConfig8;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App8 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig8.class);
        ctx.registerBean("bookService", BookServiceImpl4.class);

        BookService bean = ctx.getBean("bookService",BookService.class);
        bean.check();

    }
}
