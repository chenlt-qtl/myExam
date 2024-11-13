package com.seed.config;

import com.seed.bean.MyPostProcessor;
import com.seed.bean.MyRegister;
import com.seed.bean.MyRegister2;
import com.seed.bean.service.impl.BookServiceImpl1;
import org.springframework.context.annotation.Import;
//优先级 BookServiceImpl1 < MyRegister2 < MyPostProcessor
@Import({MyPostProcessor.class, BookServiceImpl1.class, MyRegister2.class})
public class SpringConfig8 {

}
