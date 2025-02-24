package com.example.feign.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MyClientsRegistrar.class})
public @interface MyClient {

    String name();

    String type() default "";
}
