package com.exam.o2_pattern.o5_adapter.login_v2;

public class Test {
    public static void main(String[] args) {
        IPassportForThird adapter = new PassportForThirdAdapter();
        adapter.loginForToken("23423324");
        adapter.loginForWechat("454534534");
    }
}
