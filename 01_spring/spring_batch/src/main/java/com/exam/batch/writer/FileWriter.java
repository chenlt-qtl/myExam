package com.exam.batch.writer;

import com.exam.batch.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FileWriter  {

    @Bean
    public FlatFileItemWriter<Student> fileItemWriter() throws Exception {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        //写到target/resource/student.txt
        //String path = "E:/student.txt";
        String path = FileWriter.class.getResource("/student.txt").getPath();
        writer.setResource(new FileSystemResource(path));

        //把对象转成字符串
        writer.setLineAggregator(new LineAggregator<Student>() {

            ObjectMapper mapper = new ObjectMapper();
            @Override
            public String aggregate(Student student) {
                String str = null;
                try {
                    str = mapper.writeValueAsString(student);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;

            }
        });
        writer.afterPropertiesSet();
        return writer;
    }
}
