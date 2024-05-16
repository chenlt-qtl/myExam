package com.seed.config;

import com.seed.bean.MyPostProcessor;
import com.seed.bean.MyRegister;
import com.seed.bean.MyRegister2;
import com.seed.bean.service.impl.BookServiceImpl1;
import org.springframework.context.annotation.Import;

@Import({MyPostProcessor.class, BookServiceImpl1.class, MyRegister2.class})
public class SpringConfig8 {

}
