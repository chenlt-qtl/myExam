package com.betta.d17_Arrays;

public class CompareStudent {

    public static int compareByAge(Student a, Student b) {
        return a.getAge() - b.getAge();
    }

    public int compareByHeight(Student a, Student b) {
        return Double.compare(a.getHeight(),b.getHeight());
    }
}
