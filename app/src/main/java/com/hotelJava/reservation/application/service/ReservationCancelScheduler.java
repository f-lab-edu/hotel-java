package com.hotelJava.reservation.application.service;

import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.reservation.adapter.persistence.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationCancelScheduler {

  private final ReservationRepository reservationRepository;

  @Scheduled(cron = "0 */1 * * * *") // 1분
  @Transactional
  public void cancelExpiredReservations() {
    LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3); // 3시간 전

    // 3시간이 지난 결제 대기 상태의 예약 리스트 조회
    List<Reservation> reservations =
        reservationRepository.findByCreatedDateTimeBeforeAndDeletedFalseAndStatusEquals(
            threeHoursAgo, ReservationStatus.PAYMENT_PENDING);

    for (Reservation reservation : reservations) {
      reservation.cancelExpiredReservation(); // 만료된 예약 취소
    }
  }
}
