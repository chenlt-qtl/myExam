package org.exam.use;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// 数字类型辅助类
public class NumberType {
    static final int INTEGER = 1;
    static final int DECIMAL = 2;

    final int type;
    final int precision; // 总长度
    final int scale;     // 小数位数

    NumberType(int type, int precision, int scale) {
        this.type = type;
        this.precision = precision;
        this.scale = scale;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setLenient(false);
        Date parse = format.parse("2025-6-1 10:23");
        System.out.println(parse);
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate parse1 = LocalDate.parse("2022-01-02 666", dateTimeFormatter);
//        System.out.println(parse1);

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDate parse1 = LocalDate.parse("2025-6-1 10:23", dateTimeFormatter);
//        System.out.println(parse1);

    }
}