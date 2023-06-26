package com.hotelJava.common.batch;

import com.hotelJava.common.batch.HotelJavaBatchConfigurationProperties;
import com.hotelJava.common.batch.JpaItemListWriter;
import com.hotelJava.room.domain.Room;
import com.hotelJava.stock.domain.Stock;
import jakarta.persistence.EntityManager;
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

  /*
     배치 처리 정보를 담고 있는 매커니즘
     어떤 Job이 실행되었으면 몇 번 실행되었고 언제 끝났는지 등 배치 처리에 대한 메타데이터를 저장
  */
  private final JobRepository jobRepository;
  private final JpaTransactionManager jpaTransactionManager;
  private final EntityManagerFactory entityManagerFactory;
  private final EntityManager entityManager;
  private final HotelJavaBatchConfigurationProperties properties;

  /*
     Job은 배치 처리 과정을 하나의 단위로 만들어 포현한 객체
  */
  @Bean(name = "maximumDayJob")
  public Job maximumDayJob() {
    return new JobBuilder("maximumDayJob", jobRepository)
        .preventRestart() // 재실행 막기
        // 오버로딩 메소드들 중 Step을 추가해서 가장 기본이되는 SimpleJobBuilder를 생성하는 메소드 사용
        .start(maximumDayStep())
        .build();
  }

  /*
     Step은 실직적인 배치 처리를 정의하고 제어 하는데 필요한 모든 정보가 있는 도메인 객체이다.
     Job을 처리하는 실질적인 단위로 쓰인다.
     모든 Job에는 1개 이상의 Step이 있어야 한다.
  */
  @Bean
  public Step maximumDayStep() {
    // 'jpaPagingItemReaderStep' 이라는 이름 StepBuilder를 생성
    return new StepBuilder("maximumDayStep", jobRepository)
        // <Reader에서 반환할 타입, Writer에 파라미터로 넘어올 타입>
        // chunkSize: Reader & Writer가 묶일 Chunk 트랜잭션 범위
        // 쓰기 시에 청크 단위로 writer() 메서드를 실행시킬 단위를 지정, 즉 커밋단위가 10개
        .<Room, List<Stock>>chunk(properties.getStock().getChunkSize(), jpaTransactionManager)
        .faultTolerant()
        .retryLimit(3)
        .retry(Exception.class) // Exception이 발생할 경우에만 최대 재시도 횟수 3회
        .reader(readerMaximumDay())
        .processor(processorMaximumDay(null))
        .writer(writerListMaximumDay())
        .build();
  }

  /*
   Step의 대상이 되는 배치 데이터를 읽어오는 인터페이스
   ItemReader의 가장 큰 장점은 데이터를 Streaming 할 수 있다는 것입니다.
   read() 메소드는 데이터를 하나씩 가져와 ItemWriter로 데이터를 전달하고, 다음 데이터를 다시 가져 옵니다.
   이를 통해 reader & processor & writer가 Chunk 단위로 수행되고 주기적으로 Commit 됩니다.
   수백, 수천 개 이상의 데이터를 한번에 가져와서 메모리에 올려놓게되면 좋지않기 때문에 배치 프로젝트에서 제공하는
   PagingItemRedaer 구현체를 사용용
  */

  // 기본 빈 생성은 싱글턴이지만 해당 메서드는 Step의 주기에 따라 새로운 빈을 생성합니다.
  // Step의 실행마다 새로운 빈을 만들기 때문에 지연 생성이 가능합니다
  @StepScope
  @Bean(destroyMethod = "")
  public JpaPagingItemReader<Room> readerMaximumDay() {
    return new JpaPagingItemReaderBuilder<Room>()
        .name("jpaPagingItemReader")
        // JPA를 사용하기 때문에 영속성 관리를 위해 EntityManager를 할당해줘야 합니다.
        .entityManagerFactory(entityManagerFactory)
        // pageSize의 default는 10
        // chunk와 pageSize 갯수를 똑같이 맞춰줘야함!
        // 예) chunk가 100, pageSize가 10이면 chunk 단위로 reader에서 processor로 전달되기에 100개를 채워야만
        // processor로 데이터가 전달된다.
        // 10번을 조회해서 문제가 아니라 JpaPagingItemReader는 페이지를 읽을때, 이전 트랜잭션 초기화를 시키기 때문이다.
        // 그러다보니 마지막 조회를 제외한 9번의 조회결과들이 트랜잭션 세션이 전부 종료되어 org.hibernate.LazyInitializationException
        // 발생
        .pageSize(properties.getStock().getChunkSize())
        .queryString("SELECT r FROM Room r WHERE stockBatchDateTime IS NULL")
        .build();
  }

  /*
     ItemProcessor는 ItemReader로 읽어 온 배치 데이터를 변환하는 역할을 수행
     ItemProcessor는 필수는 아님
     비즈니스 로직의 분리 : ItemWriter는 저장 수행하고, ItemProcessor는 로직 처리만 수행해 역할을 명확하게 분리
     읽어온 배치 데이터와 씌여질 데이터의 타입이 다를 경우에 대응할 수 있기 때문
  */
  @Bean
  @StepScope
  public ItemProcessor<Room, List<Stock>> processorMaximumDay(
      @Value("#{jobParameters[now]}") LocalDateTime now) {
    return room -> {
      room.changeStockBatchDateTime(now);
      Room mergedRoom = entityManager.merge(room);

      return IntStream.range(0, properties.getStock().getDay())
          .mapToObj(
              i ->
                  Stock.builder()
                      .room(mergedRoom)
                      .date(LocalDate.now().plusDays(i))
                      .quantity(properties.getStock().getQuantity())
                      .build())
          .collect(Collectors.toList());
    };
  }

  /*
     Reader와 Processor를 거쳐 처리된 Item을 Chunk 단위 만큼 쌓은 뒤 이를 Writer에 전달한다.
     Reader의 read()는 Item 하나를 반환하는 반면, Writer의 write()는 인자로 Item List를 받는다.
  */
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
