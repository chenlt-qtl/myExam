package com.exam.D21_Java_adva.D03_Annotation;

@MyAnnotation(aaa="c",ccc={"d"})
@MyAnnotation2
@MyAnnotation3(value = "test",bbb={"ccc"})
public class D01_annotationDemo {

    @MyAnnotation(aaa="e",ccc={"f"})
    @MyAnnotatioin1("ccc")
    @MyAnnotation3(value = "test1",bbb={"666"})
    public void test1(){

    }
}
