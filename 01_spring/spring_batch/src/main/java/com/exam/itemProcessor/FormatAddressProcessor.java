package com.exam.itemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FormatAddressProcessor implements ItemProcessor<Student,Student> {
    @Override
    public Student process(Student student) throws Exception {
        student.setAddress("中国"+student.getAddress());
        return student;
    }
}
