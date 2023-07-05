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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class StockMaximumDayBatchConfig {

  private final JobRepository jobRepository;
  private final JpaTransactionManager jpaTransactionManager;
  private final EntityManagerFactory entityManagerFactory;
  private final HotelJavaBatchConfigurationProperties properties;

  @Bean(name = "maximumDayJob")
  public Job maximumDayJob() {
    return new JobBuilder("maximumDayJob", jobRepository)
        .preventRestart()
        .start(maximumDayStep())
        .build();
  }

  @Bean
  public Step maximumDayStep() {
    return new StepBuilder("maximumDayStep", jobRepository)
        .<Room, List<Stock>>chunk(properties.getStock().getChunkSize(), jpaTransactionManager)
        .faultTolerant()
        .retryLimit(3)
        .retry(Exception.class)
        .reader(readerMaximumDay())
        .processor(processorMaximumDay(null))
        .writer(writerListMaximumDay())
        .build();
  }

  @StepScope
  @Bean(destroyMethod = "")
  public JpaPagingItemReader<Room> readerMaximumDay() {
    return new JpaPagingItemReaderBuilder<Room>()
        .name("jpaPagingItemReader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(properties.getStock().getChunkSize())
        .queryString("SELECT r FROM Room r WHERE stockBatchDateTime IS NULL")
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Room, List<Stock>> processorMaximumDay(
      @Value("#{jobParameters[now]}") LocalDateTime now) {
    return room -> {
      room.changeStockBatchDateTime(now);

      return IntStream.range(0, properties.getStock().getDay())
          .mapToObj(
              i ->
                  Stock.builder()
                      .room(room)
                      .date(LocalDate.now().plusDays(i))
                      .quantity(properties.getStock().getQuantity())
                      .build())
          .collect(Collectors.toList());
    };
  }

  //    private ItemWriter<List<Stock>> writerListMaximumDay() { // ItemWriter 인터페이스를 구현한 Custom
  //      return chunk -> {
  //        for (List<Stock> stocks : chunk) {
  //          stockRepository.saveAll(stocks);
  //        }
  //      };
  //    }

  private JpaItemListWriter<Stock> writerListMaximumDay() {
    JpaItemWriter<Stock> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

    return new JpaItemListWriter<>(jpaItemWriter);
  }
}
