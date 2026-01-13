package com.exam.D21_Java_adva.D03_Annotation;

public @interface MyAnnotation {
    String aaa();
    boolean bbb() default true;
    String[] ccc();
}
