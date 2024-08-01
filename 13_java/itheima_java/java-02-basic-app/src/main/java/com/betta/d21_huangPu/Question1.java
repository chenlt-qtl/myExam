package com.betta.d21_huangPu;

import java.util.*;

/**
 * **需求**
 * <p>
 * 目前有100名囚犯，每个囚犯的编号是1-200之间的随机数。
 * 现在要求依次随机生成100名囚犯的编号（要求这些囚犯的编号是不能重复的），然后让他们依次站成一排。(注：位置是从1开始计数的)
 * 接下来，国王命令手下先干掉全部奇数位置处的人。
 * 剩下的人，又从新按位置1开始，再次干掉全部奇数位置处的人，依此类推，直到最后剩下一个人为止，剩下的这个人为幸存者。
 * <p>
 * **具体功能点的要求如下：**
 * <p>
 * 请输出幸存者的编号，以及他第一次所占的位置值是多少。
 * <p>
 * **评分细则**
 * <p>
 * * 能做出第一步：生产100个随机编号，且占位成功的，给3分。
 * <p>
 * * 能成功删除奇数位置处的数据的，给5分。
 * <p>
 * * 能正确获取结果的给2分。
 */
public class Question1 {

    public static Random random = new Random();

    public static void main(String[] args) {

        Map<Integer, Integer> all = new LinkedHashMap();
        int i = 1;

        System.out.println("---生成编号---");
        while (i <= 100) {
            //生产100个随机编号，且占位成功的
            int j = random.nextInt(200) + 1;
            if (!all.containsKey(j)) {
                all.put(j, i);
                i++;
            }
        }
        System.out.println("生成的编号是：" + all);

        while (all.size() > 1) {
            Map<Integer, Integer> temp = new LinkedHashMap();
            int k = 1;
            for (Map.Entry<Integer, Integer> entry : all.entrySet()) {
                if (k % 2 == 0) {
                    temp.put(entry.getKey(), entry.getValue());
                }
                k++;
            }
            all = temp;
            System.out.println("删除后：" + all);
        }
        //获取幸运者。
        all.forEach((k, v) -> {
            System.out.println("幸运儿的编号是：" + k + ",第一次占的位置是" + v);
        });

    }

    public static Set<Integer> genNumbers(int size) {
        Set<Integer> set = new TreeSet<>();
        while (true) {
            int i = random.nextInt(200) + 1;
            set.add(i);
            if (set.size() >= size) {
                break;
            }
        }
        System.out.println("生成的编号长度为:" + set.size());
        System.out.println("生成的编号为:" + set);
        return set;
    }
}
