package com.betta.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型例子
 */
public class Demo {
    public static void main(String[] args) {


        Sheel s = new Sheel();
        Car c = new Car();
        BaoMa baoMa = new BaoMa();
        BenChi benChi = new BenChi();

        //接受Car及其子类
        get(baoMa);
        get(c);

        //使用通配符
        //接受任意类型
        List<Sheel> list = new ArrayList<>();
        set(list);

        //接受Car及其子类
        List<BaoMa> list1 = new ArrayList<>();
        set1(list1);

        //接受Car及其父类
        List<Sheel> list2 = new ArrayList<>();
        set2(list2);
    }

    //泛型方法
    public static <T extends Car> T get(T t){
        return t;
    }

    public static void set(List<?> a){

    }

    /**
     * 接收Car及其子类
     * @param a
     */
    public static void set1(List<? extends Car> a){

    }

    public static void set2(List<? super Car> a){

    }
}

class Sheel <T>{
    T a;

    /**
     * 不是泛型方法，泛型不是方法定义的
     * @return
     */
    public T get(){
        return a;
    }
}

class Car extends Sheel {

}

class BaoMa extends Car {

}

class BenChi extends Car {

}