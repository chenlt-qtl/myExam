package com.seed.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.seed.domain.Article;
import com.seed.controller.utils.ResultBean;
import com.seed.service.IArticleService;
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
        List<Article> all = articleService.getAll();
        return ResultBean.success(all);
    }

    @PostMapping
    public ResultBean save(@RequestBody Article article) {
        return new ResultBean(articleService.add(article));
    }

    @PutMapping
    public ResultBean update(@RequestBody Article article) {

        return new ResultBean(articleService.update(article));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        return new ResultBean(articleService.delete(id));
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
