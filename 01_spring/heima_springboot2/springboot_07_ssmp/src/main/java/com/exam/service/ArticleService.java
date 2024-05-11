package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exam.domain.Article;

import java.util.List;

public interface ArticleService {
    Boolean save(Article article);
    Boolean update(Article article);
    Boolean delete(Integer id);
    Article getById(Integer id);
    List<Article> getAll();
    IPage<Article> getPage(int cur, int pageSize);
}
