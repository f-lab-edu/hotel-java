package com.hotelJava.accommodation.application.port.in;

import com.hotelJava.accommodation.application.port.in.result.SearchAccommodationResult;
import com.hotelJava.accommodation.application.port.out.persistence.AccommodationSearchCondition;
import com.hotelJava.accommodation.domain.AccommodationType;
import java.time.LocalDate;
import java.util.List;

public interface SearchAccommodationQuery {

  List<SearchAccommodationResult> search(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount);

  List<SearchAccommodationResult> search(AccommodationSearchCondition condition);
}
