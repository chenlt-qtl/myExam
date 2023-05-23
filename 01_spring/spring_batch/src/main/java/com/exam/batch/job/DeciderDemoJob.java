package com.exam.batch.job;

import com.exam.batch.component.MyDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * job决策器
 */
@Configuration
public class DeciderDemoJob extends BaseJob{

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("step1");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("even");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("odd");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }

    @Bean
    public Job deciderJob(){
        return jobBuilderFactory.get("deciderJob")
                .start(step1())
                .next(myDecider())
                .from(myDecider()).on("even")
                .to(step2()).from(myDecider()).on("odd").to(step3())
                .from(step3()).on("*").to(myDecider())
                .end()
                .build();
    }
}
