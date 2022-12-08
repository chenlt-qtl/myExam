package com.exam.batch.job;

import com.exam.batch.entity.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ItemProcessDemoJob extends BaseJob{

    @Autowired
    private ItemProcessor<Student,Student> formatAddressProcessor;

    @Autowired
    private ItemProcessor<Student,Student> idFilterProcessor;

    @Bean
    public Job itemProcessJob() {
        return jobBuilderFactory.get("itemProcessJob")
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