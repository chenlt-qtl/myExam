package com.seed.bean.service.impl;

import com.seed.bean.service.BookService;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl1 implements BookService {
    @Override
    public void check() {
        System.out.println("BookServiceImpl1 .");
    }
}
