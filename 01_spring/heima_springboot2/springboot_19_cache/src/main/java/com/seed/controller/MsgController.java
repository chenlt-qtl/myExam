package com.seed.controller;

import com.seed.domain.Article;
import com.seed.controller.utils.ResultBean;
import com.seed.service.IMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private IMsgService msgService;


    @PostMapping
    public ResultBean check(String tel, String code) {
        return ResultBean.success(msgService.check(tel, code));
    }


    @GetMapping("/{tel}")
    public ResultBean<Article> get(@PathVariable String tel) {
        String code = msgService.get(tel);
        return ResultBean.success(code);
    }

}
