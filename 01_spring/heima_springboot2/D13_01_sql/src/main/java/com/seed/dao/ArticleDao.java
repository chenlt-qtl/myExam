package com.seed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {

}
