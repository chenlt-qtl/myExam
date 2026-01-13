package com.exam.o99_homework.before;


import com.exam.o99_homework.rule.BooleanModelRule;
import com.exam.o99_homework.rule.DictModelRule;
import com.exam.o99_homework.rule.IModelRule;
import com.exam.o99_homework.rule.NumModelRule;


public class Before {

    public static void main(String[] args) {
        int indicatorType = 3;
        String value = "A001";
        IModelRule rule = null;
        switch (indicatorType) {
            case 1:
                //数字
                rule = new NumModelRule();
                break;
            case 2:
                //布尔
                rule = new BooleanModelRule();
                break;
            case 3:
                //字典
                rule = new DictModelRule();
                break;
            default:
                rule = new NumModelRule();
                break;
        }

        rule.computeScore(value);
    }
}
