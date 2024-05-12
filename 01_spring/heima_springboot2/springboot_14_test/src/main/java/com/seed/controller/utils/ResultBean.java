package com.seed.controller.utils;

import lombok.Data;

@Data
public class ResultBean<T> {

    boolean flag;
    T data;
    String msg;

    public ResultBean() {
    }

    public ResultBean(boolean flag) {
        this.flag = flag;
    }

    public static ResultBean success() {
        ResultBean resultBean = new ResultBean();
        resultBean.setFlag(true);
        return resultBean;
    }

    public static <T> ResultBean success(T object) {
        ResultBean resultBean = new ResultBean();
        resultBean.setFlag(true);
        resultBean.setData(object);
        return resultBean;
    }


    public static ResultBean fail(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setFlag(false);
        resultBean.setMsg(msg);
        return resultBean;
    }


}
