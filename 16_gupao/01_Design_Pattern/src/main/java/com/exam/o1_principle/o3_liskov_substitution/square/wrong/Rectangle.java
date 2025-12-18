package com.exam.o1_principle.o3_liskov_substitution.square.wrong;

/**
 * 长方形
 */
public class Rectangle {
    private long width;
    private long height;

    public void setWidth(long width) {
        this.width = width;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }
}
