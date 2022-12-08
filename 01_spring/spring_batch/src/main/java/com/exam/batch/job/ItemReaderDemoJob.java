package com.exam.batch.job;

import com.exam.batch.reader.MyReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Configuration
public class ItemReaderDemoJob extends BaseJob{

    @Bean
    public Job itemReaderJob() {
        return jobBuilderFactory.get("itemReaderJob")
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