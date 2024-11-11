package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.domain.Article;

public interface IArticleService extends IService<Article> {

    IPage<Article> pageSearch(int cur,int pageSize, Article article);
}
