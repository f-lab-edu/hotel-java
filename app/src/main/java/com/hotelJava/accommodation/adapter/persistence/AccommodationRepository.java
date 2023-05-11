package com.hotelJava.accommodation.adapter.persistence;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.reservation.domain.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

  @Query(
      "select a from Accommodation a "
          + "left join a.rooms r "
          + "left join r.reservations res "
          + "where a.address.firstLocation = :firstLocation "
          + "AND a.address.secondLocation = :secondLocation "
          + "AND a.type = :type "
          + "AND r.maxOccupancy >= :guestCount "
          + "AND (:name = '' OR a.name LIKE %:name%) "
          + "AND (res.id is null OR res.status = :status) "
          + "AND :checkInDate >= CURRENT_DATE "
          + "AND :checkOutDate > CURRENT_DATE AND "
          + "NOT EXISTS (SELECT res from Reservation res "
          + "where res.checkDate.checkInDate < :checkOutDate AND res.checkDate.checkOutDate > :checkInDate)")
  List<Accommodation> findAccommodations(
      @Param("type") AccommodationType type,
      @Param("firstLocation") String firstLocation,
      @Param("secondLocation") String secondLocation,
      @Param("name") String name,
      @Param("checkInDate") LocalDate checkInDate,
      @Param("checkOutDate") LocalDate checkOutDate,
      @Param("guestCount") int guestCount,
      @Param("status") ReservationStatus status);

  boolean existsByName(String name);
}
