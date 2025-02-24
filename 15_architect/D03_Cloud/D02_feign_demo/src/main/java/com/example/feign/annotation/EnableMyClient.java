package com.example.feign.annotation;


import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(MyClientsRegistrar.class)
public @interface EnableMyClient {
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}
