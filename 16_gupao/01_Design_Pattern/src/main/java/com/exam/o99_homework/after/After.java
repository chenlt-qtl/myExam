package com.exam.o99_homework.after;
import com.exam.o99_homework.rule.IModelRule;

public class After {
    public static void main(String[] args) {
        int indicatorType = 3;
        String value = "A001";
        IModelRule modelRule = new ModelRuleFactory().create(indicatorType);
        modelRule.computeScore(value);
    }
}
