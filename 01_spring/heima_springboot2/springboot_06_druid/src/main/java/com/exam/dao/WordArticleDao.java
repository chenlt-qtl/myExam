package com.exam.dao;

import com.exam.domain.WordArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WordArticleDao {

    @Select("select * from word_article where id = #{id}")
    public WordArticle getById(Integer id);
}
