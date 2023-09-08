package com.zerobase.job;

import com.zerobase.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class WeeklyJob extends QuartzJobBean {
    private final MovieService movieService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        movieService.deletePastOpenMovies();
        movieService.saveOpenMovies();
    }
}
