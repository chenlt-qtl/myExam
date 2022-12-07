package com.exam.skipListener;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        list.forEach(item-> System.out.println(item));
    }
}
