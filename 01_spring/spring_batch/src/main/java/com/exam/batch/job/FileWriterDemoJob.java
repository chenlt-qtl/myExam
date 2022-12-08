package com.exam.batch.job;

import com.exam.batch.entity.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileWriterDemoJob extends BaseJob {


    @Bean
    public Job fileWriterJob() {
        return jobBuilderFactory.get("fileWriterJob")
                .start(fileWriterStep()).build();
    }


    @Bean
    public Step fileWriterStep() {

        return stepBuilderFactory.get("fileWriterStep")
                .<Student, Student>chunk(10)
                .reader(dbRederReader)
                .writer(itemWriter)
                .build();

    }

}