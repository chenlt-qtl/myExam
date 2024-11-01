package com.exam.D17_Exam_1.Timer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TimerTask;

public class TimeTask extends TimerTask {
    // 高考开始时间
    private LocalDateTime startTime;

    // 构造器，对高考的开始时间进行初始化
    public TimeTask() {
        // 补全代码
        startTime = LocalDateTime.of(2025, 6, 7, 9, 0, 0);

    }

    // 每一秒执行一次该方法
    @Override
    public void run() {
        // 补全代码：完成倒计时效果
        System.out.println("2025高考倒计时");
        System.out.println("2025高考时间：2025年6月7日 星期" + startTime.getDayOfWeek().getValue());
        System.out.println("现在距离高考还有");
        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(now, startTime);
        System.out.println(between.toDaysPart() + "天" + between.toHoursPart() + "时" +
                between.toMinutesPart() + "分" + between.toSecondsPart() + "秒");

    }

}
