package com.seed.bean;


import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.stereotype.Component;


//@ConditionalOnClass(name = "com.seed.bean.Wolf")
@ConditionalOnMissingClass({"com.seed.bean.Wolf"})
//@ConditionalOnBean(name = "com.seed.bean.Mouse")
@ConditionalOnBean(name = "jerry")
@ConditionalOnWebApplication //是web项目
//@ConditionalOnNotWebApplication //不是web项目
@Component("Tom")
public class Cat {
}
