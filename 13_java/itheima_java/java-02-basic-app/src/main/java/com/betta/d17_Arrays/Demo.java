package com.betta.d17_Arrays;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        int[] a = new int[]{10, 20, 30, 40};

        //打印
        System.out.println(Arrays.toString(a));

        //截取中间一段包前不包后
        System.out.println(Arrays.toString(Arrays.copyOfRange(a, 2, 4)));

        //考贝到新数组
        int[] b = Arrays.copyOf(a, 20);
        System.out.println(Arrays.toString(b));
        int[] c = Arrays.copyOf(a, 2);
        System.out.println(Arrays.toString(c));

        //setAll  (把price里的价格都乘以0.8)
        double[] price = {56.6, 9.9, 30.6};
        Arrays.setAll(price, new IntToDoubleFunction() {
            @Override
            public double applyAsDouble(int value) {
                return BigDecimal.valueOf(price[value]).multiply(BigDecimal.valueOf(0.8)).doubleValue();
            }
        });
        System.out.println(Arrays.toString(price));

        //sort 排序 默认升序
        Arrays.sort(price);
        System.out.println(Arrays.toString(price));
    }
}


