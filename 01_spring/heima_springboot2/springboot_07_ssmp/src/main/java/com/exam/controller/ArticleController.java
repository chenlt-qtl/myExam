package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.controller.utils.ResultBean;
import com.exam.domain.Article;
import com.exam.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @GetMapping
    public ResultBean<List<Article>> getAll() {
        List<Article> all = articleService.list();
        return ResultBean.success(all);
    }

    @PostMapping
    public ResultBean save(@RequestBody Article article) {
        return new ResultBean(articleService.addArticle(article));
    }

    @PutMapping
    public ResultBean update(@RequestBody Article article) {

        return new ResultBean(articleService.updateById(article));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        return new ResultBean(articleService.removeById(id));
    }

    @GetMapping("/{id}")
    public ResultBean<Article> getById(@PathVariable Integer id) {
        return ResultBean.success(articleService.getById(id));
    }

    @GetMapping("/{cur}/{pageSize}")
    public ResultBean<IPage<Article>> getPage(@PathVariable Integer cur, @PathVariable Integer pageSize, Article article) {
        IPage<Article> page = articleService.pageSearch(cur, pageSize, article);
        if (cur > page.getPages()) {
            cur = (int) page.getPages();
            page = articleService.pageSearch(cur, pageSize, article);
        }
        return ResultBean.success(page);
    }
}
