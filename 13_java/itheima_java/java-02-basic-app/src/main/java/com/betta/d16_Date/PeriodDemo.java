package com.betta.d16_Date;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate oldDate = LocalDate.of(1999,10,13);
        Period between = Period.between(now,oldDate);
        System.out.println(between.getYears());
        System.out.println(between.getMonths());
        System.out.println(between.getDays());
    }
}
