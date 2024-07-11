package com.betta.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 现在假如购物车中存储了如下这些商品:
 * Java入门，宁夏杞，黑杞，人字拖，特级构杞，枸杞子。
 * 现在用户不想买构杞了，选择了批量删除，请完成该需求。
 */
public class ArrayListTest {

    public static void main(String[] args) {
        //初始化购物车
        List<String> cart = new ArrayList<>();
        cart.add("Java入门");
        cart.add("宁夏枸杞");
        cart.add("黑枸杞");
        cart.add("人字拖");
        cart.add("特级枸杞");
        cart.add("枸杞子");

        //删除所有枸杞相关的数据
//        List<String> gq = cart.stream().filter(pro -> pro.contains("枸杞")).collect(Collectors.toList());
//        cart.removeAll(gq);
//        System.out.println(cart);

        //倒着遍厉，防止漏删
        for (int i = cart.size()-1; i >=0; i--) {
            if(cart.get(i).contains("枸杞")){
                cart.remove(i);
            }
        }
        System.out.println(cart);
    }
}
