package com.exam.batch.job;

import com.exam.batch.component.CustomerExceptionProcessor;
import com.exam.batch.component.MySkipListener;
import com.exam.batch.entity.Student;
import com.exam.batch.writer.PrintWriter;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
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

    @Autowired
    protected JobLauncher jobLauncher;


    @Autowired
    protected CustomerExceptionProcessor retryItemProcessor;

    @Autowired
    protected PrintWriter printWriter;


    @Autowired
    protected CustomerExceptionProcessor customerExceptionProcessor;

    @Autowired
    protected PrintWriter skipItemWriter;

    @Autowired
    protected MySkipListener skipListener;
}
