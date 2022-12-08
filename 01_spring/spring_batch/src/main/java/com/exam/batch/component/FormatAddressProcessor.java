package com.exam.batch.component;

import com.exam.batch.entity.Student;
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
