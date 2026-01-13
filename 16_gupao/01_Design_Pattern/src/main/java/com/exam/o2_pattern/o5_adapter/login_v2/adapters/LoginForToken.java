package com.exam.o2_pattern.o5_adapter.login_v2.adapters;

import com.exam.o2_pattern.o5_adapter.login_v2.ResultMsg;

public class LoginForToken extends AbstractLoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForToken;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return super.loginForRegister(id,null);
    }

}
