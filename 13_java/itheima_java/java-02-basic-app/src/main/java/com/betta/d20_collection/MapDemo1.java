package com.betta.d20_collection;

import com.sun.jdi.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 某个班级有80个学生，现在要组织去秋游，大家对A B C D四个景点进行投票
 * 统计哪个景点得票最多
 */
public class MapDemo1 {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        String[] options = {"科技馆", "博物馆", "海底世界", "观音山"};
        Random r = new Random();
        for (int i = 0; i < 80; i++) {
            vote(options, r, map);
        }
        System.out.println("投票结果:"+map);
        Map.Entry<String,Integer> max = new Map.Entry<String, Integer>() {
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public Integer getValue() {
                return null;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        };
    }

    public static void vote(String[] options, Random r, Map<String, Integer> result) {
        int nextInt = r.nextInt(4);
        String option = options[nextInt];
        if (result.containsKey(option)) {
            result.put(option, result.get(option) + 1);
        } else {
            result.put(option, 1);
        }
    }
}
