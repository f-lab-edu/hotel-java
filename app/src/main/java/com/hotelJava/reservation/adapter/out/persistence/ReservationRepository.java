package com.hotelJava.reservation.adapter.out.persistence;

import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Optional<Reservation> findByReservationNo(String reservationNo);

  List<Reservation> findByStatus(ReservationStatus status);
}
