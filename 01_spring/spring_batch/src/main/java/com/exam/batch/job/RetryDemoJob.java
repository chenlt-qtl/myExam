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
public class RetryDemoJob extends BaseJob{

    @Bean
    public Job retryJob(){
        return jobBuilderFactory.get("retryJob")
                .start(retryDemoStep()).build();
    }


    @Bean
    public Step retryDemoStep() {
        return stepBuilderFactory.get("retryDemoStep")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(retryItemProcessor)
                .writer(printWriter)
                .faultTolerant()
                .retry(CustomerRetryException.class)
                .retryLimit(5)
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