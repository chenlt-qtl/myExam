package com.exam.D10_Object_Adva.D01_Genericity;

public class Sheel <T>{
    T a;

    /**
     * 不是泛型方法，泛型不是方法定义的
     * @return
     */
    public T get(){
        return a;
    }
}
