package com.hotelJava.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StockJobConfiguration {
  @Bean
  public Job simpleJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("addStockJob", jobRepository).start(step).build();
  }

  @Bean
  @JobScope
  public Step step(
      @Value("#{jobParameters[requestDate]}") String requestDate,
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager) {
    return new StepBuilder("step1", jobRepository)
        .tasklet(
            ((contribution, chunkContext) -> {
              log.info(">>>>> This is Step1");
              log.info(">>>>> requestDate = {}", requestDate);
              return RepeatStatus.FINISHED;
            }),
            transactionManager)
        .build();
  }
}
