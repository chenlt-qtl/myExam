package com.exam.o2_pattern.o5_adapter.login_v1;

public interface IPassportForThird {

    ResultMsg loginForWechat(String openId);

    ResultMsg loginForToken(String token);

    ResultMsg loginForRegister(String username, String password);
}
