package com.seed;

import com.seed.domain.Article;
import com.seed.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
//@Transactional
public class AppTest {


    @Autowired
    private IArticleService articleService;

    @Test
    public void test(){
        Article article = articleService.getById(2);
        System.out.println(article);
    }

}
