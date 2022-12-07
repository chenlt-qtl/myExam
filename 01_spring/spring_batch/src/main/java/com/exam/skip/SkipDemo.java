package com.exam.skip;

import com.exam.retry.CustomerRetryException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SkipDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustomerExceptionProcessor customerExceptionProcessor;

    @Autowired
    private PrintWriter skipItemWriter;

    @Bean
    public Job skipDemoJob(){
        return jobBuilderFactory.get("skipDemoJob")
                .start(skipStep()).build();
    }


    @Bean
    public Step skipStep() {
        return stepBuilderFactory.get("skipStep")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(customerExceptionProcessor)
                .writer(skipItemWriter)
                .faultTolerant()
                .skip(CustomerRetryException.class)
                .skipLimit(5)
                .build();

    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        for(int i = 0; i<60; i++){
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

}