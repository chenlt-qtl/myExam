package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exam.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleServiceTestCase {

    @Autowired
    private ArticleService articleService;

    @Test
    void getById() {
        System.out.println(articleService.getById(2));
    }

    @Test
    void save() {
        Article article = new Article();
        article.setTitle("test10");
        article.setComment("comment10");
        System.out.println(articleService.save(article));
    }

    @Test
    void update() {
        Article article = new Article();
        article.setId(2);
        article.setTitle("test1");
        article.setComment("comment");
        System.out.println(articleService.update(article));
    }

    @Test
    void delete() {
        System.out.println(articleService.delete(1));
    }

    @Test
    void getAll() {
        System.out.println(articleService.getAll());
    }

    @Test
    void getByPage() {

        IPage<Article> page = articleService.getPage(3, 3);
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
    }

    @Test
    void getBy() {
//        QueryWrapper<Article> qw = new QueryWrapper<>();
//        qw.like("title","1");
//        articleDao.selectList(qw);
    }

    @Test
    void getBy1() {

//        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
//        qw.like(Article::getTitle,"1");
//        articleDao.selectList(qw);
    }

    @Test
    void getBy2() {
//        String name = null;
//        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
//        //相当 if(name != null){...}
//        qw.like(name!=null,Article::getTitle,"1");
//        articleDao.selectList(qw);
    }
}
