package com.seed;

import com.seed.controller.Article;
import com.seed.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DaoTest {


    @Autowired
    private IArticleService articleService;
    @Test
    public void test(){
        Article article = new Article();
        article.setComment("comment");
        article.setTitle("title");
        articleService.save(article);
    }

}
