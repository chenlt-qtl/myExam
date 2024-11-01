package com.exam.D10_Object_Adva.D03_Enum;

public enum A {
    //枚举类的第一行必须罗列的是枚举对象的名字
    X,Y,Z;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
