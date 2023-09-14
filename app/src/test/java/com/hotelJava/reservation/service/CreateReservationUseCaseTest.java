package com.hotelJava.reservation.service;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.room.adapter.persistence.RoomRepository;
import com.hotelJava.room.domain.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // 스프링 부트 애플리케이션 컨텍스트를 로드
@ActiveProfiles("test")
public class CreateReservationUseCaseTest {

    @Autowired
    @Qualifier("eagerReservationService")
    private CreateReservationUseCase sut;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @Transactional
    @DisplayName("사용자가 선택한 숙박 기간(체크인, 체크아웃)에 해당하는 객실을 예약한다.")
    void 객실_예약() throws InterruptedException {
        // given
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀의 개수 지정
        CountDownLatch latch = new CountDownLatch(threadCount);

        CheckDate checkDate = new CheckDate(now(), 2);
        CreateReservationRequest createReservationRequest =
                TestFixture.getCreateReservationRequestDto(checkDate);
        Room room = roomRepository.findById(1L).orElseThrow();

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    sut.createReservation(1L, "test@test.com", createReservationRequest);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        // 해당일 재고 개수만큼 예약 테스트 => 남은 재고가 없어야함(0 개)
        assertThat(room.getStocks().get(0).getQuantity()).isEqualTo(0);
        assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
    }
}
