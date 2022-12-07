package com.exam.itemProcessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableBatchProcessing
public class ItemProcessDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbRederReader")
    private ItemReader<Student> dbRederReader;


    @Autowired
    private ItemProcessor<Student,Student> formatAddressProcessor;

    @Autowired
    private ItemProcessor<Student,Student> idFilterProcessor;

    @Bean
    public Job itemProcessJob() {
        return jobBuilderFactory.get("itemProcessJob" + System.currentTimeMillis())
                .start(itemProcessStep())
                .build();
    }

    @Bean
    public Step itemProcessStep() {
        return stepBuilderFactory.get("itemProcessStep")
                .<Student, Student>chunk(2)
                .reader(dbRederReader)
                .processor(processor())
                .writer(list -> {
                    list.forEach(item -> System.out.println(item + "..."));
                })
                .build();

    }

    //有多种处理数据的方式
    @Bean
    public CompositeItemProcessor<Student,Student> processor(){
        CompositeItemProcessor<Student,Student> processor = new CompositeItemProcessor<>();
        List<ItemProcessor<Student,Student>> delagates = new ArrayList();
        delagates.add(formatAddressProcessor);
        delagates.add(idFilterProcessor);
        processor.setDelegates(delagates);
        return processor;

    }

}