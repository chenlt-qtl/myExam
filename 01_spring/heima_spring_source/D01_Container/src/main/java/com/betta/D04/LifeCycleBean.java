package com.betta.D04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("LifeCycleBean")
public class LifeCycleBean {
    Logger logger = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        logger.info("1.构造");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}") String home) {
        logger.info("2.依赖注入：{}", home);
    }

    @PostConstruct
    public void init(){
        logger.info("3.初始化");
    }

    @PreDestroy
    public void destory(){
        logger.info("4.销毁");
    }
}
