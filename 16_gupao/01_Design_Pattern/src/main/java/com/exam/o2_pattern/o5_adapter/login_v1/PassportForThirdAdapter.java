package com.exam.o2_pattern.o5_adapter.login_v1;

public class PassportForThirdAdapter extends PassportService implements IPassportForThird{
    @Override
    public ResultMsg loginForWechat(String openId) {
        return loginForRegister(openId,null);
    }

    @Override
    public ResultMsg loginForToken(String token) {
        return loginForRegister(token,null);
    }

    @Override
    public ResultMsg loginForRegister(String username, String password) {
        if(password == null){
            password = "EMPTY";
        }
        super.register(username, password);
        return super.login(username, password);
    }


}
