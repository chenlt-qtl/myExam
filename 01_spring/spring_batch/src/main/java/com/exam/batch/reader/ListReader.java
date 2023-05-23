package com.exam.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ListReader {

    @Bean
    private ItemReader<String> listItemReader() {
        return new ListItemReader<>(Arrays.asList("java","spring","mysql","php"));
    }

}
