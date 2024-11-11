package com.seed.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "testcase.article")
public class MyDomain {

    private int id;
    private long id2;
    private int type;
    private String title;
    private String comment;
}
