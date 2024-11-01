package com.exam.D11_API.D04;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class D03_Format {
    public static void main(String[] args) {
        //1.创建格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        //2.格式化时间
        LocalDateTime now = LocalDateTime.now();
        String format = dateTimeFormatter.format(now);
        System.out.println(format);
        System.out.println(now.format(dateTimeFormatter));

        ZonedDateTime z1 = ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println(z1.format(dateTimeFormatter));

        //3. 解析时间
        String time = "2024年07月23日 12:52:04";
        System.out.println(LocalDateTime.parse(time, dateTimeFormatter));

    }
}
