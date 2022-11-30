package com.exam.fileWriter;

import com.exam.dbReader.Student;
import com.exam.decider.MyDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FileWriterDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    @Qualifier("dbRederReader")
    private ItemReader<Student> dbRederReader;

    @Autowired
    @Qualifier("fileItemWriter")
    private ItemWriter<Student> fileItemWriter;

    @Bean
    public Job fileWriterDemoJob(){
        return jobBuilderFactory.get("fileWriterDemoJob"+System.currentTimeMillis())
                .start(fileWriterStep()).build();
    }


    @Bean
    public Step fileWriterStep() {

        return stepBuilderFactory.get("fileWriterStep")
                .<Student,Student>chunk(10)
                .reader(dbRederReader)
                .writer(fileItemWriter)
                .build();

    }

}