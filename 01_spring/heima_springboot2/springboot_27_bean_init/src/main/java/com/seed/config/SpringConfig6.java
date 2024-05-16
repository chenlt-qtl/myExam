package com.seed.config;

import com.seed.bean.MyImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ComponentScan(basePackages = {"com.seed"})
@Import({MyImportSelector.class})
public class SpringConfig6 {

}
