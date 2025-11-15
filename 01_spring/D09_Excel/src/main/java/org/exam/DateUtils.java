package org.exam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateUtils {
    // 支持的日期格式列表（可根据实际需求扩展）
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
        new SimpleDateFormat("yyyy-MM-dd"),
        new SimpleDateFormat("yyyy/MM/dd"),
        new SimpleDateFormat("MM-dd-yyyy"),
        new SimpleDateFormat("dd/MM/yyyy"),
        new SimpleDateFormat("yyyy年MM月dd日"),
        new SimpleDateFormat("M/d/yyyy"),    // 如 3/28/2016
        new SimpleDateFormat("MM/d/yyyy"),   // 如 03/28/2016
        new SimpleDateFormat("M/dd/yyyy")    // 如 3/08/2016
    );

    // 尝试解析字符串为日期，并返回标准格式（yyyy-MM-dd）
    public static String parseAndFormat(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String trimmed = dateStr.trim();
        for (SimpleDateFormat format : DATE_FORMATS) {
            try {
                Date date = format.parse(trimmed);
                return new SimpleDateFormat("yyyy-MM-dd").format(date); // 转换为标准格式
            } catch (ParseException e) {
                // 尝试下一种格式
            }
        }
        return null; // 无法解析为日期
    }

    // 判断字符串是否为日期格式（不严格校验，仅用于类型识别）
    public static boolean isDate(String dateStr) {
        return parseAndFormat(dateStr) != null;
    }
}