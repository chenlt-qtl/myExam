package com.betta.d21_huangPu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * - User 实体类，包含如下属性
 * <p>
 * - private Long id;  // 用户id 名
 * - private String gender;  //性别
 * - private LocalDate birthday;  //生日
 * - 注意需要提供 set和get方法，以及toString方法
 * <p>
 * - 新建测试类，类中 main 方法，在方法中完成如下业务逻辑：
 * <p>
 * - **业务一：**
 * <p>
 * - 有如下字符串，里面包含多个用户信息数据，现在需要你解析这个字符串，获取里面的用户数据，并封装到User对象中
 * <p>
 * - 多个User对象在添加到List<User> 集合中
 * <p>
 * ```java
 * String userStrs = "10001:张三:男:1990-01-01#10002:李四:女:1989-01-09#10003:王五:男:1999-09-09#10004:刘备:男:1899-01-01#10005:孙悟空:男:1900-01-01#10006:张三:女:1999-01-01#10007:刘备:女:1999-01-01#10008:张三:女:2003-07-01#10009:猪八戒:男:1900-01-01";
 * ```
 * <p>
 * - 注意：
 * <p>
 * - 字符串中的规则如下，多个用户用 # 拼接，用户的信息之间用 : 拼接。
 * - 其中用户id和生日是需要进行类型转换的，其中id需要将String转成Long，生日需要将String转成LocalDate
 * <p>
 * - **业务二：**
 * <p>
 * - 遍历上面获取的List<User> 集合，统计里面每个名字出现的次数。
 * - 封装到Map<String,Integer>集合中，集合的key就是名字，value就是名字出现的次数。
 * - 最后遍历打印map数据，打印内容如下：
 * - 张三：3次
 * - 李四：5次
 */
public class Question2 {

    public static void main(String[] args) {
        String userStrs = "10001:张三:男:1990-01-01#10002:李四:女:1989-01-09#10003:王五:男:1999-09-09#10004:刘备:男:1899-01-01#10005:孙悟空:男:1900-01-01#10006:张三:女:1999-01-01#10007:刘备:女:1999-01-01#10008:张三:女:2003-07-01#10009:猪八戒:男:1900-01-01";

        //获取里面的用户数据，并封装到User对象中,多个User对象在添加到List<User> 集合中
        //其中用户id和生日是需要进行类型转换的，其中id需要将String转成Long，生日需要将String转成LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<User> list = Stream.of(userStrs.split("#")).map(s -> {
            String[] split = s.split(":");
            long id = Long.parseLong(split[0]);
            LocalDate birthday = LocalDate.parse(split[3], formatter);
            return new User(id, split[1], split[2], birthday);
        }).collect(Collectors.toList());

        System.out.println("用户数据:" + list);

        //遍历上面获取的List<User> 集合，统计里面每个名字出现的次数。
        //封装到Map<String,Integer>集合中，集合的key就是名字，value就是名字出现的次数。
        //最后遍历打印map数据，打印内容如下：
        //- 张三：3次
        //- 李四：5次
        Map<String, Integer> map = new HashMap<>();
        list.stream().forEach(u -> {
            String name = u.getName();
            if (map.containsKey(name)) {
                map.put(name, map.get(name) + 1);
            } else {
                map.put(name, 1);
            }
        });
        map.forEach((k, v) -> System.out.println("-" + k + ":" + v + "次"));
    }
}

class User {
    private Long id;  // 用户id
    private String name;  // 用户姓名
    private String gender;  //性别
    private LocalDate birthday;  //生日

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User() {
    }

    public User(Long id, String name, String gender, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}


