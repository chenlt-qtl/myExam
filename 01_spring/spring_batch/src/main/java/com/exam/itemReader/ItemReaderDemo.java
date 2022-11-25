package com.exam.itemReader;

import com.exam.listener.MyChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class ItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job itemReaderJob() {
        return jobBuilderFactory.get("itemReaderJob" + System.currentTimeMillis())
                .start(itemReaderStep())
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return stepBuilderFactory.get("itemReaderStep")
                .chunk(2)
                .reader(itemRederReader())
                .writer(list -> {
                    list.forEach(item-> System.out.println(item+"..."));
                })
                .build();

    }

    @Bean
    public MyReader itemRederReader() {

        List<String> data = Arrays.asList("cat","dog","duck","flog","pig","rabbit");
        return new MyReader(data);
    }
}