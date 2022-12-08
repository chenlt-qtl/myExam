package com.exam.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/job")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("dbReaderJob")
    private Job dbReaderJob;


    @Autowired
    @Qualifier("fileWriterJob")
    private Job fileWriterJob;

    @Autowired
    @Qualifier("flowJob")
    private Job flowJob;

    @Autowired
    @Qualifier("splitJob")
    private Job splitJob;


    @Autowired
    @Qualifier("deciderJob")
    private Job deciderJob;


    @Autowired
    @Qualifier("nestedJob")
    private Job nestedJob;

    @Autowired
    @Qualifier("listenerJob")
    private Job listenerJob;

    @Autowired
    @Qualifier("parameterJob")
    private Job parameterJob;


    @Autowired
    @Qualifier("itemReaderJob")
    private Job itemReaderJob;


    @Autowired
    @Qualifier("itemProcessJob")
    private Job itemProcessJob;


    @Autowired
    @Qualifier("errorJob")
    private Job errorJob;

    @Autowired
    @Qualifier("retryJob")
    private Job retryJob;


    @Autowired
    @Qualifier("skipJob")
    private Job skipJob;

    @Autowired
    @Qualifier("skipListenerJob")
    private Job skipListenerJob;


    @Autowired
    @Qualifier("onToJob")
    private Job onToJob;

    @RequestMapping("/dbReader")
    public String dbReader() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(dbReaderJob);
        return "job success";
    }

    @RequestMapping("/fileWriter")
    public String fileWriter() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(fileWriterJob);
        return "job success";
    }

    @RequestMapping("/flowJob")
    public String flowJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(flowJob);
        return "job success";
    }


    @RequestMapping("/splitJob")
    public String splitJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(splitJob);
        return "job success";
    }


    @RequestMapping("/deciderJob")
    public String deciderJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(deciderJob);
        return "job success";
    }


    @RequestMapping("/nestedJob")
    public String nestedJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(nestedJob);
        return "job success";
    }


    @RequestMapping("/listenerJob")
    public String listenerJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(listenerJob);
        return "job success";
    }

    @RequestMapping("/parameterJob/{msg}")
    public String parameterJob(@PathVariable String msg) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addDate("Date",new Date()).addString("info",msg).toJobParameters();
        jobLauncher.run(parameterJob,parameters);
        return "job success";
    }


    @RequestMapping("/itemReaderJob")
    public String itemReaderJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(itemReaderJob);
        return "job success";
    }


    @RequestMapping("/itemProcessJob")
    public String itemProcessJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(itemProcessJob);
        return "job success";
    }


    @RequestMapping("/errorDemoJob")
    public String errorDemoJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(errorJob);
        return "job success";
    }


    @RequestMapping("/retryJob")
    public String retryJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(retryJob);
        return "job success";
    }

    @RequestMapping("/skipJob")
    public String skipJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(skipJob);
        return "job success";
    }


    @RequestMapping("/skipListenerJob")
    public String skipListenerJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(skipListenerJob);
        return "job success";
    }

    @RequestMapping("/onToJob")
    public String onToJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        runJob(onToJob);
        return "job success";
    }

    private void runJob(Job job) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addDate("Date",new Date()).toJobParameters();
        jobLauncher.run(job,parameters);
    }

}
