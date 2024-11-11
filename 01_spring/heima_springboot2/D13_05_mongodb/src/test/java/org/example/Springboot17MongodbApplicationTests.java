package org.example;

import org.example.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
class Springboot17MongodbApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void testSave() {
        int index = 2;
        Article article = new Article();
        article.setId(index);
        article.setTitle("title"+index);
        article.setComment("comment"+index);
        mongoTemplate.save(article);
    }

    @Test
    void testFind() {
        System.out.println(mongoTemplate.findAll(Article.class));
    }


}
