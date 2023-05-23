package com.exam.batch.component;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.util.Map;

public class MyStepListener implements StepExecutionListener {

    private Map<String, JobParameter> parameterMap;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameterMap = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    public Map<String, JobParameter> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, JobParameter> parameterMap) {
        this.parameterMap = parameterMap;
    }
}
