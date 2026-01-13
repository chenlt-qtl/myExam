package com.exam.D11_API.D04;

import java.time.*;

/**
 * JDK8新增日期对象
 */

public class D02_Local_Date_Zone_Instant {
    public static void main(String[] args) {
//        localDate();
//        zone();
        instant();
    }

    /**
     * 替换Calendar
     */
    public static void localDate() {
        LocalDate ld = LocalDate.now();
        System.out.println(ld.getYear());
        System.out.println(ld.getMonthValue());
        System.out.println(ld.getDayOfMonth());
        //修改
        LocalDate d1 = ld.withYear(2099);
        System.out.println(d1);
        LocalDate d2 = ld.withMonth(5);
        System.out.println(d2);

        //计算
        LocalDate d3 = ld.plusYears(3);
        System.out.println(d3);
        LocalDate d4 = ld.minusMonths(3);
        System.out.println(d4);

        //指定日期
        LocalDate d5 = LocalDate.of(2011, 3, 6);
        System.out.println(d5);
        LocalDate d6 = LocalDate.of(2011, 3, 6);

        //比较
        System.out.println(d5.equals(d6));
        System.out.println(d5.isAfter(d6));

        System.out.println("----------------------------------");

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime.getHour());

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getYear());

        //转换
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime1 = localDateTime.toLocalTime();
        LocalDateTime localDateTime1 = LocalDateTime.of(localDate, localTime1);
    }

    public static void zone() {

        //ZoneId
        //获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
        //获取所有的zoneId
        System.out.println(ZoneId.getAvailableZoneIds());

        //指定zoneId
        ZoneId z1 = ZoneId.of("America/New_York");
        System.out.println(z1);

        //ZoneDateTime 带时区的时间
        ZonedDateTime now = ZonedDateTime.now(z1);
        System.out.println(now);

        //世界标准时间
        ZonedDateTime z2 = ZonedDateTime.now(Clock.systemUTC());
        System.out.println(z2);

        //服务器时间
        ZonedDateTime z3 = ZonedDateTime.now();
        System.out.println(z3);

        //计算等跟LocalDateTime一样
    }

    /**
     * 代替Date
     */
    public static void instant() {
        //当前时间
        Instant now = Instant.now();
        System.out.println(now);

        //获取总秒数
        long epochSecond = now.getEpochSecond();
        System.out.println(epochSecond);

        //获取纳秒数
        int nano = now.getNano();
        System.out.println(nano);
    }
}
