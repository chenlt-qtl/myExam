package com.exam.o1_principle.o3_liskov_substitution.method_param;

import java.util.HashMap;

public class Base {

    public void method(HashMap map) {
        System.out.println("父类执行");
    }
}
