package com.exam.batch.job;


import com.exam.batch.entity.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DbReaderDemoJob extends BaseJob{


    @Bean
    public Job dbReaderJob() {
        return jobBuilderFactory.get("dbReaderJob")
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