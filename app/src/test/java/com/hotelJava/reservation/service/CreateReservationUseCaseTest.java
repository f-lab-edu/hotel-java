package com.hotelJava.reservation.service;

import com.hotelJava.TestFixture;
import com.hotelJava.accommodation.application.port.BeforeEachInit;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.PaymentType;
import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.reservation.facade.ReservationFacade;
import com.hotelJava.room.adapter.persistence.RoomRepository;
import com.hotelJava.room.domain.Room;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CreateReservationUseCaseTest extends BeforeEachInit {

    @Autowired
    @Qualifier("eagerReservationService")
    private CreateReservationUseCase sut;

    @Autowired
    private ReservationFacade reservationFacade;

    @SpyBean
    private FindMemberPort findMemberPort;

//    @SpyBean
    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("사용자가 선택한 숙박 기간(체크인, 체크아웃)과 해당하는 객실에 예약한다.")
    void 객실_예약_Race_Condition_발생() throws InterruptedException {
        // given
        int threadCount = 100;

        // ExecutorService: 비동기로 실행하는 작업을 단순화하여 사용할 수 있게 도와줌
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀의 개수 지정

        // 100개의 요청이 끝날때까지 기다려야하므로 CountDownLatch 활용
        // CountDownLatch: 다른 쓰레드에서 수행중인 작업이 완료될때 까지 대기할 수 있도록 도와주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount); // Latch 할 개수 지정

        Member member = TestFixture.getMember();
        doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

        Room room = TestFixture.getRoom(10, 5, now(), 2);

        // 반환될 값 또는 예외: Optional.of(room)
        // 모의 객체: roomRepository
        // 반환 값을 지정하려는 메서드: findById
        // 메서드 호출 시 전달되는 인자: anyLong()
        doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());

        CheckDate checkDate = new CheckDate(now(), 2);
        CreateReservationRequest createReservationRequest =
                TestFixture.getCreateReservationRequestDto(checkDate);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    sut.createReservation(anyLong(), member.getEmail(), createReservationRequest);
                } finally {
                    latch.countDown(); // countDown(): Latch 의 카운터가 1개씩 감소
                }
            });
        }
        latch.await(); // await(): Latch 의 카운터가 0이 될 때까지 기다림

        // then
        // 해당일 재고 개수만큼 예약 테스트 => 남은 재고가 없어야함(0 개)
//        assertThat(room.getStocks().get(0).getQuantity()).isNotEqualTo(0);
        assertThat(room.getStocks().get(0).getQuantity()).isEqualTo(0);
        assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isFalse();

//    assertDoesNotThrow(
//        () ->
//            sut.createReservation(
//                room.getId(), member.getEmail(), createReservationRequest));
    }

    @Test
    @DisplayName("Optimistic Lock(낙관적 락) 활용")
    void 객실_예약_낙관적락_활용() throws InterruptedException {
        // given
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀의 개수 지정
        CountDownLatch latch = new CountDownLatch(threadCount); // Latch 할 개수 지정

        Member member = TestFixture.getMember();
        doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

        Room room = TestFixture.getRoom(10, 5, now(), 2);
        doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());

        CheckDate checkDate = new CheckDate(now(), 2);
        CreateReservationRequest createReservationRequest =
                TestFixture.getCreateReservationRequestDto(checkDate);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationFacade.createReservation(anyLong(), member.getEmail(), createReservationRequest);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown(); // countDown(): Latch 의 카운터가 1개씩 감소
                }
            });
        }
        latch.await(); // await(): Latch 의 카운터가 0이 될 때까지 기다림

        // then
        // 해당일 재고 개수만큼 예약 테스트 => 남은 재고가 없어야함(0 개)
        assertThat(room.getStocks().get(0).getQuantity()).isEqualTo(0);
        assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
    }

    @Test
    @DisplayName("Optimistic Lock(낙관적 락) 활용 (실제 레포지토리 사용)")
    void 객실_예약_낙관적락_활용2() throws InterruptedException {
        // given
        int threadCount = 1;
        CountDownLatch latch = new CountDownLatch(threadCount); // Latch 할 개수 지정
        ExecutorService executorService = Executors.newFixedThreadPool(32); // 스레드 풀의 개수 지정

        Member member = TestFixture.getMember();
        doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

//        Room room = TestFixture.getRoom(10, 5, now(), 2);
//        doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());

        Room room = roomRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println(">>>>>" + room.getStocks());

        CheckDate checkDate = new CheckDate(now(), 2);
        CreateReservationRequest createReservationRequest = CreateReservationRequest.builder()
                .checkDate(checkDate)
                .guestName("박경탁")
                .guestPhone("010-1234-1234")
                .numberOfGuests(1)
                .paymentType(PaymentType.KAKAO_PAY)
                .build();

        // when
//        for (int i = 0; i < 1; i++) {
//            executorService.submit(() -> {
//                try {
//                    reservationFacade.createReservation(1L, member.getEmail(), createReservationRequest);
////                    sut.createReservation(1L, member.getEmail(), createReservationRequest);
//                    log.debug("hello");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown(); // countDown(): Latch 의 카운터가 1개씩 감소
//                }
//            });
//        }
//        latch.await(); // await(): Latch 의 카운터가 0이 될 때까지 기다림

        for (int i = 0; i < threadCount; i++) {
//            sut.createReservation(1L, member.getEmail(), createReservationRequest);
            reservationFacade.createReservation(1L, member.getEmail(), createReservationRequest);
        }

        // then

        assertThat(room.getStocks().get(0).getQuantity()).isEqualTo(4);


        // 해당일 재고 개수만큼 예약 테스트 => 남은 재고가 없어야함(0 개)
//        assertThat(room.getStocks().get(0).getQuantity()).isEqualTo(0);
//        assertThat(room.isNotEnoughStockAtCheckDate(checkDate)).isTrue();
    }
}
