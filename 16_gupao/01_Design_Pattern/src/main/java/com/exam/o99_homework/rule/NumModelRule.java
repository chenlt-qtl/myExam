package com.exam.o99_homework.rule;

public class NumModelRule implements IModelRule {
    @Override
    public void computeScore(String value) {
        System.out.println("计算数值类型:"+value+" 的分数");
    }
}