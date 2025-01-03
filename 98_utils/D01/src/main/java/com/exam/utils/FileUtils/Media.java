package com.exam.utils.FileUtils;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Media {
    private String name;
    private long second;
    private LocalDate finishDate;

    public String getTimeStr() {
        long hour = second / (60 * 60);
        long min = second % (60 * 60) / 60;
        return hour + ":" + min;
    }

    @Override
    public String toString() {
        return name +
                ", 时长：" + getTimeStr();
    }
}
