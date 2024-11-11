package com.exam.dao.impl;

import com.exam.dao.IBookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao implements IBookDao {
    @Override
    public void save() {
        System.out.println("book dao is running...");
    }
}
