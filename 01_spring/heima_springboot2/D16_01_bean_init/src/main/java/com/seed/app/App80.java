package com.seed.app;

import com.seed.bean.service.BookService;
import com.seed.bean.service.impl.BookServiceImpl4;
import com.seed.config.SpringConfig8;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 后处理器实现对容器中bean的最终裁定
 */
public class App80 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig8.class);
//        ctx.registerBean("bookService", BookServiceImpl4.class);

        BookService bean = ctx.getBean("bookService",BookService.class);
        bean.check();

    }
}
