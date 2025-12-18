package com.exam.o2_pattern.o5_adapter.login_v2;

import lombok.Data;

@Data
public class ResultMsg {
    private String msg;
    private Object data;
    private int code;

    public ResultMsg(String msg, Object data, int code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }
}
