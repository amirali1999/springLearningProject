package com.example.demo.config.quartz.service;

import com.example.demo.config.quartz.info.TimerInfo;
import com.example.demo.config.quartz.utils.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Slf4j
public class SchedulerService {
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class jobClass, final TimerInfo info){
        final JobDetail jobDetail = TimerUtils.buildJobDetails(jobClass,info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass,info);

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);
        }
    }
    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        } catch (SchedulerException e){
            log.error(e.getMessage(),e);
        }
    }
    @PreDestroy
    public void preDestory(){
        try {
            scheduler.shutdown();
        }catch (SchedulerException e){
            log.error(e.getMessage(),e);

        }
    }
}

