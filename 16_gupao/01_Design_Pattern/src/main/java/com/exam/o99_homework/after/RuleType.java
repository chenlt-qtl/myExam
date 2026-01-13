package com.exam.o99_homework.after;

import com.exam.o99_homework.rule.BooleanModelRule;
import com.exam.o99_homework.rule.DictModelRule;
import com.exam.o99_homework.rule.IModelRule;
import com.exam.o99_homework.rule.NumModelRule;

/**
 * 规则类型
 */
public enum RuleType {

    NUM(1, NumModelRule.class),//整数类型
    BOOLEAN(2, BooleanModelRule.class),//布尔类型
    DICT(3, DictModelRule.class);//长整数类型

    private int num;
    private Class clazz;

    public int getNum() {
        return num;
    }

    public Class getClazz() {
        return clazz;
    }

    RuleType(int num, Class clazz) {
        this.num = num;
        this.clazz = clazz;
    }

    /**
     * 根据值获取对应的class，默认是数值类型
     *
     * @param type 类型
     * @return 返回类型
     */
    public static Class<? extends IModelRule> getByCode(int type) {
        for (RuleType ruleType : RuleType.values()) {
            if (ruleType.getNum() == type) {
                return ruleType.getClazz();
            }
        }
        return NUM.getClazz(); // 默认是数值型
    }
}