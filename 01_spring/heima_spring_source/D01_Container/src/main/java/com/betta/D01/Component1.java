package com.betta.D01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Component1 {

    public static final Logger logger = LoggerFactory.getLogger(Component1.class);

    @EventListener
    public void listen(UserRegisteredEvent event){
        logger.info("{}",event);
    }
}
