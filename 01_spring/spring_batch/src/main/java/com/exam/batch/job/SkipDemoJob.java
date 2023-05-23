package com.exam.batch.job;

import com.exam.batch.component.CustomerRetryException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SkipDemoJob extends BaseJob{

    @Bean
    public Job skipJob(){
        return jobBuilderFactory.get("skipJob")
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

    public ItemReader<String> reader() {
        List<String> items = new ArrayList<>();
        for(int i = 0; i<60; i++){
            items.add(String.valueOf(i));
        }
        return new ListItemReader<>(items);
    }

}