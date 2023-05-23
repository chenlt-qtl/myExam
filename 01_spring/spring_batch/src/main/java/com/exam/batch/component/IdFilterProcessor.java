package com.exam.batch.component;

import com.exam.batch.entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class IdFilterProcessor implements ItemProcessor<Student,Student> {

    /**
     * 只要ID是双数的数据
     * @param student
     * @return
     * @throws Exception
     */
    @Override
    public Student process(Student student) throws Exception {
        if(student.getId()%2==0){
            return student;
        }else {
            return null;
        }
    }
}
