package com.hotelJava.common.config;

import com.hotelJava.room.domain.Room;
import com.hotelJava.stock.domain.Stock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.batch")
public class StockLastDayBatchConfig {

  private final JobRepository jobRepository;
  private final JpaTransactionManager jpaTransactionManager;
  private final EntityManagerFactory entityManagerFactory;
  private final EntityManager entityManager;

  @Value("${spring.batch.chunk.size}")
  private int chunkSize;

  @Value("${spring.batch.stock.add.day}")
  private int stockAddDay;

  @Value("${spring.batch.stock.add.quantity}")
  private int stockAddQuantity;

  @Bean(name = "lastDayJob")
  public Job lastDayJob() {
    return new JobBuilder("lastDayJob", jobRepository)
        .preventRestart()
        .start(lastDayStep())
        .build();
  }

  @Bean
  public Step lastDayStep() {
    return new StepBuilder("lastDayStep", jobRepository)
        .<Room, Stock>chunk(chunkSize, jpaTransactionManager)
        .reader(readerLastDay())
        .processor(processorLastDay(null))
        .writer(writerLastDay())
        .build();
  }

  @StepScope
  @Bean(destroyMethod = "")
  public JpaPagingItemReader<Room> readerLastDay() {
    return new JpaPagingItemReaderBuilder<Room>()
        .name("readerLastDay")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(chunkSize)
        .queryString("SELECT r FROM Room r WHERE r.stockBatchDateTime IS NOT NULL")
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Room, Stock> processorLastDay(@Value("#{jobParameters[now]}") LocalDateTime now) {
    return room -> {
      room.changeStockBatchDateTime(now);
      Room mergedRoom = entityManager.merge(room);

      return Stock.builder()
          .room(mergedRoom)
          .date(LocalDate.now().plusDays(stockAddDay))
          .quantity(stockAddQuantity)
          .build();
    };
  }

  private JpaItemWriter<Stock> writerLastDay() {
    JpaItemWriter<Stock> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

    return jpaItemWriter;
  }
}
