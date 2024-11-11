package com.seed;

import com.seed.dao.ArticleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
//@Transactional
public class DaoTest {


    @Autowired
    private ArticleDao articleDao;

    @Test
    public void test(){
        Article article = articleDao.selectById(2);
        System.out.println(article);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testJdbcTemplateQuery() {
        String sql = "select * from eng_article";
        RowMapper<Article> rm = new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                article.setComment(rs.getString("comment"));
                return article;
            }
        };
        List<Article> maps = jdbcTemplate.query(sql, rm);
        System.out.println(maps);

    }

    @Test
    void testJdbcTemplateSave() {
        String sql = "insert into eng_article values (2,'title2','comment2')";
        jdbcTemplate.update(sql);
    }

}
