package com.exam.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.SsmpApplication;
import com.exam.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SsmpApplication.class)
class ArticleDaoTests {

    @Autowired
    ArticleDao articleDao;
    @Test
    void select() {
        System.out.println(articleDao.selectById(1));
    }

    @Test
    void save() {
        Article article = new Article();
        article.setTitle("test1");
        article.setComment("comment");
        System.out.println(articleDao.insert(article));
    }

    @Test
    void update() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("test1");
        article.setComment("comment");
        System.out.println(articleDao.updateById(article));
    }

    @Test
    void delete() {
        System.out.println(articleDao.deleteById(1));
    }

    @Test
    void getAll() {
        System.out.println(articleDao.selectByMap(null));
    }

    @Test
    void getByPage() {

        IPage page = new Page(1,3);
        articleDao.selectPage(page,null);
        System.out.println(page.getRecords());
    }

    @Test
    void getBy() {
        QueryWrapper<Article> qw = new QueryWrapper<>();
        qw.like("title","1");
        articleDao.selectList(qw);
    }

    @Test
    void getBy1() {

        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        qw.like(Article::getTitle,"1");
        articleDao.selectList(qw);
    }

    @Test
    void getBy2() {
        String name = null;
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        //相当 if(name != null){...}
        qw.like(name!=null,Article::getTitle,"1");
        articleDao.selectList(qw);
    }
}
