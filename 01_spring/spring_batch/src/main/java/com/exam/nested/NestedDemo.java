package com.exam.nested;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * job嵌套
 */
@Configuration
@EnableBatchProcessing
public class NestedDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Job childJob1;

    @Autowired
    private Job childJob2;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job nestedJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("nestedJob")
                .start(jobStep1(jobRepository, transactionManager))
                .next(jobStep2(jobRepository, transactionManager)).build();
    }

    @Bean
    public Step jobStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("jobStep2"))
                .job(childJob2)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step jobStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("jobStep1"))
                .job(childJob1)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }
}
