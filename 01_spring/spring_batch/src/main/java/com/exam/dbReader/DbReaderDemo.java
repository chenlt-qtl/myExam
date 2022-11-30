package com.exam.dbReader;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class DbReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbWriter")
    private ItemWriter<Student> dbWriter;

    @Autowired
    @Qualifier("dbRederReader")
    private ItemReader<Student> dbRederReader;

    @Bean
    public Job dbReaderJob() {
        return jobBuilderFactory.get("dbReaderJob" + System.currentTimeMillis())
                .start(dbReaderStep())
                .build();
    }

    @Bean
    public Step dbReaderStep() {
        return stepBuilderFactory.get("dbReaderStep")
                .<Student,Student>chunk(2)
                .reader(dbRederReader)
                .writer(dbWriter)
                .build();

    }

}