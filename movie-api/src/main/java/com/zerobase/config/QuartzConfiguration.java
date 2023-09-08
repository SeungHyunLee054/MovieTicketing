package com.zerobase.config;

import com.zerobase.job.MonthlyJob;
import com.zerobase.job.WeeklyJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {
    private final Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException {
        JobDetail weeklyJob = JobBuilder.newJob(WeeklyJob.class)
                .withIdentity("weeklyJob")
                .build();
        Trigger weeklyTrigger = TriggerBuilder.newTrigger()
                .withIdentity("weeklyJobTrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0 0 ? * MON"))
                .forJob("weeklyJob")
                .build();

        scheduler.scheduleJob(weeklyJob, weeklyTrigger);

        JobDetail monthlyJob = JobBuilder.newJob(MonthlyJob.class)
                .withIdentity("monthlyJob")
                .build();

        Trigger monthlyTrigger = TriggerBuilder.newTrigger()
                .withIdentity("monthlyJobTrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0 0 1 * ?"))
                .forJob("monthlyJob")
                .build();

        scheduler.scheduleJob(monthlyJob, monthlyTrigger);
    }
}
