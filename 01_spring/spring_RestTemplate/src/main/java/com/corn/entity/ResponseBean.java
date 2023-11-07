package com.corn.entity;

import lombok.Data;

@Data
public class ResponseBean {
    private Object data;
    private int success;
    private String message;

    public static ResponseBean successData(Object data) {
        ResponseBean result = new ResponseBean();
        result.setData(data);
        result.setSuccess(1);
        return result;
    }
}
