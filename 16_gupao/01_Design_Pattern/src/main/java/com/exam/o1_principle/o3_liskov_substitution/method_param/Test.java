package com.exam.o1_principle.o3_liskov_substitution.method_param;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Child child = new Child();
        Base father = new Child();
        HashMap map = new HashMap();
        child.method(map);//执行子类
        father.method(map);
    }
}
