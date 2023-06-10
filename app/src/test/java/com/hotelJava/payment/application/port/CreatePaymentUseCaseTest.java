package com.hotelJava.payment.application.port;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.payment.dto.CreatePaymentRequest;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.adapter.persistence.ReservationRepository;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.adapter.persistence.RoomRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CreatePaymentUseCaseTest {

  @Autowired
  @Qualifier("paymentByKakaoPay")
  private CreatePaymentUseCase sut;

  @SpyBean private RoomRepository roomRepository;

  @SpyBean private ReservationRepository reservationRepository;

  @Test
  @DisplayName("결제 검증 과정에서 iamport 서버 에러")
  void 객실_결제_실패() {
    // given
    CheckDate checkDate = new CheckDate(now(), 10);
    CreatePaymentRequest createPaymentRequest = TestFixture.getCreatePaymentRequestDto();

    Reservation reservation = TestFixture.getReservation(checkDate);
    doReturn(Optional.of(reservation))
        .when(reservationRepository)
        .findByReservationNo(reservation.getReservationNo());
    Room room = reservation.getRoom();
    doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());

    // when & then
    assertThatThrownBy(() -> sut.createPayment(anyLong(), createPaymentRequest))
        .isInstanceOf(InternalServerException.class)
        .hasMessageContaining("요청을 정상 처리하지 못하였습니다");
  }
}
