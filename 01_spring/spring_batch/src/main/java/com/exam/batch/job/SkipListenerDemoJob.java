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
public class SkipListenerDemoJob extends BaseJob{

    @Bean
    public Job skipListenerJob(){
        return jobBuilderFactory.get("skipListenerJob")
                .start(skipStep()).build();
    }


    public Step skipStep() {
        return stepBuilderFactory.get("skipStep")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(customerExceptionProcessor)
                .writer(printWriter)
                .faultTolerant()
                .skip(CustomerRetryException.class)
                .skipLimit(5)
                .listener(skipListener)
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