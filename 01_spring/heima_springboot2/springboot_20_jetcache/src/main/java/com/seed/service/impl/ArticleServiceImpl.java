package com.seed.service.impl;

import com.alicp.jetcache.anno.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seed.domain.Article;
import com.seed.dao.ArticleDao;
import com.seed.service.IArticleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleDao dao;

//    private HashMap<Integer,Article> cache = new HashMap();

    @Override
    public Boolean add(Article article) {
        return dao.insert(article) > 0;
    }

    @Override
    @CacheUpdate(name = "article_",key="#article.id",value="#article")
    public Boolean update(Article article) {
        return dao.updateById(article) > 0;
    }

    @Override
    @CacheInvalidate(name = "article_", key="#id")
    public Boolean delete(Integer id) {
        return dao.deleteById(id) > 0;
    }

    @Override
    @Cached(name="article_",key = "#id",expire = 3600,timeUnit = TimeUnit.SECONDS,cacheType = CacheType.LOCAL)
    @CacheRefresh(refresh = 50000)
    public Article getById(Integer id) {
        return dao.selectById(id);
    }

    @Override
    public List<Article> getAll() {
        return dao.selectList(null);
    }

    @Override
    public IPage<Article> pageSearch(int cur, int pageSize, Article article) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Strings.isNotBlank(article.getTitle()), Article::getTitle, article.getTitle());
        return dao.selectPage(new Page<>(cur, pageSize), wrapper);
    }
}
