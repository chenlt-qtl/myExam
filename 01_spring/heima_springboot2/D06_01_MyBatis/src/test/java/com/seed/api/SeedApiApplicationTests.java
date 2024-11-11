package com.seed.api;

import com.exam.BootApplication;
import com.exam.dao.WordArticleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BootApplication.class)
class SeedApiApplicationTests {

    @Autowired
    WordArticleDao wordArticleDao;
    @Test
    void contextLoads() {
        System.out.println(wordArticleDao.getById(1));
    }

}
