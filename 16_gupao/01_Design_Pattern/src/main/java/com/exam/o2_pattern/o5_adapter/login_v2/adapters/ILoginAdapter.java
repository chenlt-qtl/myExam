package com.exam.o2_pattern.o5_adapter.login_v2.adapters;

import com.exam.o2_pattern.o5_adapter.login_v2.ResultMsg;

public interface ILoginAdapter {

    boolean support(Object adapter);
    ResultMsg login(String id, Object adapter);
}
