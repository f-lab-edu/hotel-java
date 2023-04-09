package com.hotelJava.accommodation.repository;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

  @Query(
      "select a from Accommodation a join a.rooms r join r.reservations res "
          + "where a.address.firstLocation = :firstLocation AND a.address.secondLocation = :secondLocation AND "
          + "a.type = :type AND r.maxOccupancy >= :guestCount AND (:name = '' OR a.name LIKE %:name%) AND "
          + "res.status <> 'RESERVATION_COMPLETED' AND :checkInDate >= CURRENT_DATE AND :checkOutDate > CURRENT_DATE AND "
          + "NOT EXISTS (SELECT res from Reservation res "
          + "where res.checkDate.checkInDate < :checkOutDate AND res.checkDate.checkOutDate > :checkInDate)")
  List<Accommodation> findAccommodations(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount);
}
