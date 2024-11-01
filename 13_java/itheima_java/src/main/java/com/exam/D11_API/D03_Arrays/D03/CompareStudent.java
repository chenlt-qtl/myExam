package com.exam.D11_API.D03_Arrays.D03;

public class CompareStudent {

    public static int compareByAge(Student a, Student b) {
        return a.getAge() - b.getAge();
    }

    public int compareByHeight(Student a, Student b) {
        return Double.compare(a.getHeight(),b.getHeight());
    }
}
