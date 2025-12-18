package com.exam.D22_exam.d02_play;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 。某个班级组织团建活动，班长给出了几个去处给大家选择，分别是“农家乐”,"轰趴"，“野外拓展”，"健身房”，本次活动每个学生是可以多选的。
 * ·现在有如下5名学生选择了如下去处。
 * 张全蛋儿 农家乐,野外拓展
 * 李二狗子 轰趴,野外拓展,健身房
 * 翠花  野外拓展，
 * 小帅 轰趴，健身房
 * 有容 农家乐
 * 是体的功能点如下:
 * 1、请找出每个去处想去的人数是多少，并输出投票最多的去处是哪个。
 * 2、请找出哪些人没有选择投票最多的去处，输出他们的名字。(本案例用不用stream流做都给分)比如:小帅，有容没有选择野外拓展。
 */
public class App {

    public static void main(String[] args) {
        String[] students = new String[]{"张全蛋儿", "李二狗子", "翠花", "小帅", "有容"};

        Map<String, List<String>> result = new HashMap<>();
        select("张全蛋儿", "轰趴,农家乐,野外拓展", result);
        select("李二狗子", "轰趴,野外拓展,健身房", result);
        select("翠花", "野外拓展", result);
        select("小帅", "健身房", result);
        select("有容", "农家乐,轰趴", result);

        //1、请找出每个去处想去的人数是多少，并输出投票最多的去处是哪个。
        System.out.println(result);
        result.keySet().forEach(key -> System.out.println(key + "的人数是:" + result.get(key).size()));
        int maxSize = result.entrySet().stream().max(Comparator.comparingInt(o -> o.getValue().size()))
                .get().getValue().size();
        //使用max只能取出来一个，如果最多的项有多个 则结果不正确

        List<String> maxList = result.entrySet().stream().filter(i -> i.getValue().size() == maxSize).map(i -> i.getKey()).collect(Collectors.toList());
        System.out.print("投票最多的去处是" + maxList);
        System.out.println();

        //2、请找出哪些人没有选择投票最多的去处，输出他们的名字。(本案例用不用stream流做都给分)比如:小帅，有容没有选择野外拓展。
        Set<String> studentNames = new HashSet<>();
        maxList.forEach(e -> studentNames.addAll(result.get(e)));
        Arrays.stream(students).filter(i->!studentNames.contains(i)).forEach(System.out::print);
        System.out.print("没有选择人最多的去处");
    }

    public static void select(String name, String names, Map<String, List<String>> result) {
        if (names != null) {
            for (String s : names.split(",")) {
                List<String> list;
                if (result.containsKey(s)) {
                    list = result.get(s);
                } else {
                    list = new ArrayList<>();
                    result.put(s, list);
                }
                list.add(name);
            }
        }
    }
}
