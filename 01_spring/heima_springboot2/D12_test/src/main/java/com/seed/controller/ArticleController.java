package com.seed.controller;

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
        List<Article> all = articleService.list();
        return ResultBean.success(all);
    }

    @PostMapping
    public ResultBean save(@RequestBody Article article) {
        return new ResultBean(articleService.save(article));
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

}
