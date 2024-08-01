package com.betta.d16_Date;

import java.util.Calendar;

public class CalendarDemo {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

        calendar.set(Calendar.MONTH,9);
        calendar.set(Calendar.DAY_OF_YEAR,125);
        System.out.println(calendar);

        calendar.add(Calendar.DAY_OF_YEAR,100);
        calendar.add(Calendar.DAY_OF_YEAR,-6);
        System.out.println(calendar);
    }
}
