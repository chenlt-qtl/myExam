package com.exam.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class OnToJobDemo extends BaseJob{

    @Bean
    public Job onToJob() {
        return jobBuilderFactory.get("onToJob")
                .start(onToStep1()).on("COMPLETED")
                .to(onToStep2()).from(onToStep2())
                .on("COMPLETED").to(onToStep3())
                .from(onToStep3()).end().build();
    }

    private Step onToStep1() {
        return stepBuilderFactory.get("onToStep1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("onToStep1...");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    private Step onToStep2() {
        return stepBuilderFactory.get("onToStep2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("onToStep2...");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    private Step onToStep3() {
        return stepBuilderFactory.get("onToStep3").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("onToStep3...");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }


}
