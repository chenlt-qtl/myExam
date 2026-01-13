package com.exam.o1_principle.o3_liskov_substitution.square.right;

public class Rectangle implements QuadRangle {
    private long width;
    private long height;
    @Override
    public long getWidth() {
        return width;
    }

    @Override
    public long getHeight() {
        return height;
    }
}
