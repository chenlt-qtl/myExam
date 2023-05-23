package com.exam.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ErrorDemoJob extends BaseJob{

    @Bean
    public Job errorJob(){
        return jobBuilderFactory.get("errorJob")
                .start(errorStep1()).next(errorStep2()).build();
    }


    @Bean
    public Step errorStep1() {
        return stepBuilderFactory.get("errorStep1")
                .tasklet(errorHandling())
                .build();

    }

    @Bean
    public Step errorStep2() {

        return stepBuilderFactory.get("errorStep2")
                .tasklet(errorHandling())
                .build();

    }

    @Bean
    @StepScope
    public Tasklet errorHandling() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();
                if(stepExecutionContext.containsKey("success")){
                    System.out.println("The second run will success");
                    return RepeatStatus.FINISHED;
                }else {
                    System.out.println("The first run will fail");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("success",true);
                    throw new RuntimeException("error...");
                }
            }
        };
    }

}