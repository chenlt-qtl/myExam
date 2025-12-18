package com.exam.o2_pattern.o5_adapter.simple;

public class Test {
    public static void main(String[] args) {
        DC5 a = new PowerAdapter(new AC220());
        a.output5V();

    }
}
