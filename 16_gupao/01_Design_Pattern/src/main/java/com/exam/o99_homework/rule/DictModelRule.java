package com.exam.o99_homework.rule;

public class DictModelRule implements IModelRule {
    @Override
    public void computeScore(String value) {
        System.out.println("计算字典值:"+value+" 的分数");
    }
}