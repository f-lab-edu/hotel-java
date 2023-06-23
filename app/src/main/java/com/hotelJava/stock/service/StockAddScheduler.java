package com.hotelJava.stock.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StockAddScheduler {

    private final JobLauncher jobLauncher;
    private final Job lastDayJob;
    private final Job maximumDayJob;

//    @Scheduled(cron = "*/5 * * * * *") // 5초
    @Scheduled(cron = "0 0 0 * * *")
    public void runAddLastDayStockJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("now", LocalDateTime.now())
                .toJobParameters();

        jobLauncher.run(lastDayJob, jobParameters);
    }

//    @Scheduled(cron = "0 */1 * * * *") // 1분
//    @Scheduled(cron = "*/10 * * * * *") // 10초
    @Scheduled(cron = "0 0 0 * * *") // 자정마다
    public void runAddMaximumDayStockJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("now", LocalDateTime.now())
                .toJobParameters();

        jobLauncher.run(maximumDayJob, jobParameters);
    }

}
