package com.exam.o99_homework.rule;

import java.math.BigDecimal;

public class BooleanModelRule implements IModelRule {

    @Override
    public void computeScore(String value) {
        System.out.println("计算布尔值:"+value+" 的分数");
    }
}