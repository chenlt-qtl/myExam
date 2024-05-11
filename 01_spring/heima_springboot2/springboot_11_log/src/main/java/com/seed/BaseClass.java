package com.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseClass {

    private final Class clazz;
    public final Logger log;

    public BaseClass(){
        clazz = this.getClass();
        log = LoggerFactory.getLogger(clazz);
    }

}
