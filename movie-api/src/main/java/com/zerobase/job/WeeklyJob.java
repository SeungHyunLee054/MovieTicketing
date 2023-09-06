package com.zerobase.job;

import com.zerobase.service.QuartzService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class WeeklyJob extends QuartzJobBean {
    private final QuartzService quartzService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        quartzService.deletePastOpenMovies();
        quartzService.saveOpenMovies();
    }
}
