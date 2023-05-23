package com.exam.batch.job;


import com.exam.batch.component.MyStepListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParametersDemoJob extends BaseJob{

    @Bean
    public Job parameterJob() {
        return jobBuilderFactory.get("parameterJob")
                .start(parameterStep())
                .build();
    }

    /**
     * Job执行的是step,Job的参数是在step中使用
     * 使用step级别的监听来传递参数
     *
     * @return
     */
    @Bean
    public Step parameterStep() {
        MyStepListener myStepListener = new MyStepListener();

        return stepBuilderFactory.get("parameterStep")
                .listener(myStepListener)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        //输出接收到的参数值
                        System.out.println(myStepListener.getParameterMap().get("info")+"parameterStep...");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
