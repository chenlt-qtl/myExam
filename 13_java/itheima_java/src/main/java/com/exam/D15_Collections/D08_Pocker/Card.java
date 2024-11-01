package com.exam.D15_Collections.D08_Pocker;

public class Card {
    /**
     * 名字
     */
    private String name;
    /**
     * 花色
     */
    private String color;

    /**
     * 等级，越大等级越高
     */
    private int level;

    public Card(String name, String color, int level) {
        this.name = name;
        this.color = color;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name + color + level;
    }
}
