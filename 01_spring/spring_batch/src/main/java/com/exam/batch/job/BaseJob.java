package com.exam.batch.job;

import com.exam.batch.entity.Student;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseJob {

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbWriter")
    protected ItemWriter<Student> dbWriter;

    @Autowired
    @Qualifier("dbRederReader")
    protected ItemReader<Student> dbRederReader;

    @Autowired
    @Qualifier("fileItemWriter")
    protected ItemWriter<Student> itemWriter;
}
