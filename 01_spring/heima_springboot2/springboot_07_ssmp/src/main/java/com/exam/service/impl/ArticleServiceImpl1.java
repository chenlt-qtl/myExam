package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.dao.ArticleDao;
import com.exam.domain.Article;
import com.exam.service.ArticleService;
import com.exam.service.IArticleService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl1 extends ServiceImpl<ArticleDao, Article> implements IArticleService {

    private Counter counter;

    public ArticleServiceImpl1(MeterRegistry registry){
        counter = registry.counter("用户付费操作次数");
    }

    @Override
    public IPage<Article> pageSearch(int cur, int pageSize, Article article) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Strings.isNotBlank(article.getTitle()),Article::getTitle,article.getTitle());
        return page(new Page<>(cur,pageSize),wrapper);
    }

    @Override
    public Boolean addArticle(Article article){
        counter.increment();
        return this.save(article);
    }
}
