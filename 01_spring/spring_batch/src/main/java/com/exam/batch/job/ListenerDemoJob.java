package com.exam.batch.job;

import com.exam.batch.component.MyChunkListener;
import com.exam.batch.component.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ListenerDemoJob extends BaseJob{

    @Autowired
    @Qualifier("listItemReader")
    private ItemReader<String> listItemReader;

    @Bean
    public Step listenerStep1() {
        return stepBuilderFactory.get("listenerStep1").<String,String>chunk(2)
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(listItemReader)
                .writer(write())
                .build();
    }

    private ItemWriter<String> write() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                list.forEach(item-> System.out.println(item));
            }
        };
    }

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(listenerStep1())
                .listener(new MyJobListener())
                .build();
    }
}
