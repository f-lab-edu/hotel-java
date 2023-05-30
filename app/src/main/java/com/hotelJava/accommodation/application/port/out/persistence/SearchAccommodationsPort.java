package com.hotelJava.accommodation.application.port.out.persistence;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import java.time.LocalDate;
import java.util.List;

public interface SearchAccommodationsPort {
  List<Accommodation> search(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount);

  List<Accommodation> search(AccommodationSearchCondition searchCondition);
}
