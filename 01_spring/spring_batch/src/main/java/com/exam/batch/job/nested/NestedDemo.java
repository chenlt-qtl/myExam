package com.exam.batch.job.nested;

import com.exam.batch.job.BaseJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
public class NestedDemo extends BaseJob {

    @Autowired
    private Job childJob1;

    @Autowired
    private Job childJob2;

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
