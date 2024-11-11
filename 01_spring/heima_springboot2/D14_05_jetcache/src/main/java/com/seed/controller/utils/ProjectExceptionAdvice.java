package com.seed.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResultBean doOtherException(Exception ex){
        ex.printStackTrace();
        return ResultBean.fail(ex.getMessage());
    }
}
