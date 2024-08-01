package com.betta.d16_Date;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DurationDemo {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oldTime = LocalDateTime.of(1999,10,13,10,10,10);
        Duration between = Duration.between(oldTime, now);
        System.out.println(between.toDays());
        System.out.println(between.toSeconds());
        System.out.println(between.toMillis());
        Arrays.toString(args);
    }
}
