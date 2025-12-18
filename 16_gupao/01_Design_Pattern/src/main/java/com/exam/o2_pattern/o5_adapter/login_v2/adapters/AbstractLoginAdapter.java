package com.exam.o2_pattern.o5_adapter.login_v2.adapters;

import com.exam.o2_pattern.o5_adapter.login_v2.PassportService;
import com.exam.o2_pattern.o5_adapter.login_v2.ResultMsg;

public abstract class AbstractLoginAdapter extends PassportService implements ILoginAdapter {

    public ResultMsg loginForRegister(String username, String password) {
        if(password == null){
            password = "EMPTY";
        }
        super.register(username, password);
        return super.login(username, password);
    }

}
