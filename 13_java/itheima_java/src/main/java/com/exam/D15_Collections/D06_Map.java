package com.exam.D15_Collections;

import java.util.HashMap;
import java.util.Map;

public class D06_Map {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Tom", 33);
        map.put("Amy", 23);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}
