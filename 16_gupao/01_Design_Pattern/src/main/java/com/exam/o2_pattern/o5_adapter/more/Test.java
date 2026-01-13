package com.exam.o2_pattern.o5_adapter.more;

public class Test {
    public static void main(String[] args) {
        DC a = new PowerAdapter(new AC220());
        a.output5V();
        a.output22V();
        a.output44V();

    }
}
