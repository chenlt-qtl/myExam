package com.exam.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.dao.ArticleDao;
import com.exam.domain.Article;
import com.exam.service.ArticleService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public Boolean save(Article article) {
        return articleDao.insert(article) > 0;
    }


    @Override
    public Boolean update(Article article) {
        return articleDao.updateById(article) > 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return articleDao.deleteById(id) > 0;
    }

    @Override
    public Article getById(Integer id) {
        return articleDao.selectById(id);
    }

    @Override
    public List<Article> getAll() {
        return articleDao.selectList(null);
    }

    @Override
    public IPage<Article> getPage(int cur, int pageSize) {
        IPage<Article> page = new Page<>(cur,pageSize);
        articleDao.selectPage(page,null);
        return page;
    }
}
