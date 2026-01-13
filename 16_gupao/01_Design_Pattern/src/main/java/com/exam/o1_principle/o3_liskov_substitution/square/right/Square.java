package com.exam.o1_principle.o3_liskov_substitution.square.right;

public class Square implements QuadRangle{
    private long length;

    @Override
    public long getWidth() {
        return length;
    }

    @Override
    public long getHeight() {
        return length;
    }
}
