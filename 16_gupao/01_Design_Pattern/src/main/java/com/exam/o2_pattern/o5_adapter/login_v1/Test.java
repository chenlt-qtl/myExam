package com.exam.o2_pattern.o5_adapter.login_v1;

public class Test {
    public static void main(String[] args) {
        PassportForThirdAdapter adapter = new PassportForThirdAdapter();
        adapter.login("tom","123456");
        adapter.loginForToken("23423324");
        adapter.loginForWechat("454534534");
    }
}
