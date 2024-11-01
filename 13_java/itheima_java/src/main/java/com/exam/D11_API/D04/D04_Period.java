package com.exam.D11_API.D04;

import java.time.LocalDate;
import java.time.Period;

public class D04_Period {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate oldDate = LocalDate.of(1999,10,13);
        Period between = Period.between(now,oldDate);
        System.out.println(between.getYears());
        System.out.println(between.getMonths());
        System.out.println(between.getDays());
    }
}
