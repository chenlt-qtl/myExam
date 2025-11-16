package org.exam.use;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    }