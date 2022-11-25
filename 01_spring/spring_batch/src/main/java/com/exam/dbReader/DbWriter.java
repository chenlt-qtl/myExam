package com.exam.dbReader;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("dbWriter")
public class DbWriter implements ItemWriter<Student> {
    @Override
    public void write(List<? extends Student> list) throws Exception {
        list.forEach(student-> System.out.println(student));
    }
}
