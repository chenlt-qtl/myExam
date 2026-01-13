package com.exam.o2_pattern.o5_adapter.login_v2;

import com.exam.o2_pattern.o5_adapter.login_v2.adapters.ILoginAdapter;
import com.exam.o2_pattern.o5_adapter.login_v2.adapters.LoginForToken;
import com.exam.o2_pattern.o5_adapter.login_v2.adapters.LoginForWechat;

public class PassportForThirdAdapter extends PassportService implements IPassportForThird {
    @Override
    public ResultMsg loginForWechat(String openId) {

        return processLogin(openId, LoginForWechat.class);
    }

    @Override
    public ResultMsg loginForToken(String token) {
        return processLogin(token, LoginForToken.class);
    }

    @Override
    public ResultMsg loginForRegister(String username, String password) {
        if(password == null){
            password = "EMPTY";
        }
        super.register(username, password);
        return super.login(username, password);
    }

    private ResultMsg processLogin(String id,Class<? extends ILoginAdapter> clazz) {
        ILoginAdapter adapter = null;
        try {
            adapter = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(adapter.support(adapter)){
            return adapter.login(id, adapter);
        }
        return null;

    }

}
