package com.example.demo.config.quartz.job;

import com.example.demo.config.quartz.info.TimerInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo info = (TimerInfo) jobDataMap.get(QuartzJob.class.getSimpleName());
        log.info(info.getCallbackData());
    }
}
