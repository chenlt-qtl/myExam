package com.betta.d41_annotation;

public @interface MyAnnotation {
    String aaa();
    boolean bbb() default true;
    String[] ccc();
}
