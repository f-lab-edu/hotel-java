package com.hotelJava.common.batch;

import com.hotelJava.room.domain.Room;
import com.hotelJava.stock.domain.Stock;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class StockLastDayBatchConfig {

  private final JobRepository jobRepository;
  private final JpaTransactionManager jpaTransactionManager;
  private final EntityManagerFactory entityManagerFactory;
  private final HotelJavaBatchConfigurationProperties properties;

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
        .<Room, Stock>chunk(properties.getStock().getChunkSize(), jpaTransactionManager)
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
        .pageSize(properties.getStock().getChunkSize())
        .queryString("SELECT r FROM Room r WHERE r.stockBatchDateTime IS NOT NULL")
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Room, Stock> processorLastDay(@Value("#{jobParameters[now]}") LocalDateTime now) {
    return room -> {
      room.changeStockBatchDateTime(now);

      return Stock.builder()
          .room(room)
          .date(LocalDate.now().plusDays(properties.getStock().getDay()))
          .quantity(properties.getStock().getQuantity())
          .build();
    };
  }

  private JpaItemWriter<Stock> writerLastDay() {
    JpaItemWriter<Stock> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

    return jpaItemWriter;
  }
}
