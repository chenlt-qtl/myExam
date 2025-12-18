package com.exam.D21_Java_adva.D03_Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})//只能用在类和方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation3 {
    String value();
    double aaa() default 100;
    String[] bbb();

}
