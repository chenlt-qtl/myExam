package com.exam.o1_principle.o3_liskov_substitution.square.wrong;

/**
 * 正方形
 */
public class Square extends Rectangle{
    private long length;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public void setWidth(long width) {
        this.length = width;
    }

    @Override
    public void setHeight(long height) {
        this.length = height;
    }

    @Override
    public long getWidth() {
        return length;
    }

    @Override
    public long getHeight() {
        return length;
    }
}
