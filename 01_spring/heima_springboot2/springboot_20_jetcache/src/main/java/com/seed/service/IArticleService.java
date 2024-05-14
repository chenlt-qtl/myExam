package com.seed.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.seed.domain.Article;

import java.util.List;

public interface IArticleService {

    Boolean add(Article article);
    Boolean update(Article article);
    Boolean delete(Integer id);
    Article getById(Integer id);
    List<Article> getAll();
    IPage<Article> pageSearch(int cur,int pageSize, Article article);
}
