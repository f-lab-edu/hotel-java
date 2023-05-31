package com.hotelJava.reservation.repository;

import com.hotelJava.reservation.domain.Reservation;
import java.time.LocalDateTime;import java.util.List;
import java.util.Optional;
import com.hotelJava.reservation.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Optional<Reservation> findByReservationNo(String reservationNo);

  List<Reservation> findByStatus(ReservationStatus status);

  List<Reservation> findByCreatedDateTimeBeforeAndDeletedFalseAndStatusEquals(LocalDateTime threeHoursAgo, ReservationStatus status);
}
