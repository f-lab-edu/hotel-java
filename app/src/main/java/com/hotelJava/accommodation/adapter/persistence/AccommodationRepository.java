package com.hotelJava.accommodation.adapter.persistence;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

  @Query(
      "select a from Accommodation a left join a.rooms r left join r.stocks i"
          + " where i.quantity > 0 and i.date >= :checkInDate and i.date < :checkOutDate"
          + " and a.address.firstLocation = :firstLocation and a.address.secondLocation = :secondLocation"
          + " and a.type = :type and r.maxOccupancy >= :guestCount and (a.name = '' or a.name like %:name%)"
          + " and :checkInDate >= CURRENT_DATE and :checkOutDate > CURRENT_DATE")
  List<Accommodation> findAccommodations(
      @Param("type") AccommodationType type,
      @Param("firstLocation") String firstLocation,
      @Param("secondLocation") String secondLocation,
      @Param("name") String name,
      @Param("checkInDate") LocalDate checkInDate,
      @Param("checkOutDate") LocalDate checkOutDate,
      @Param("guestCount") int guestCount);
  boolean existsByName(String name);
}
