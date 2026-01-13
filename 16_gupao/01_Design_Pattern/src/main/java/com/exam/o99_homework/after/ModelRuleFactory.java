package com.exam.o99_homework.after;

import com.exam.o99_homework.rule.IModelRule;

public class ModelRuleFactory {
    public IModelRule create(Integer type) {
        try {
            //根据type生成对应的规则对象
            return RuleType.getByCode(type).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
