package com.seed.config;

import com.seed.bean.Dog;
import org.springframework.context.annotation.Import;

@Import({Dog.class, DbConfig.class})
public class SpringConfig4 {

}
