package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.FindAccommodationResponse;
import com.hotelJava.member.domain.Role;
import java.time.LocalDate;
import java.util.List;

public interface FindAccommodationQuery {

  List<FindAccommodationResponse> findAccommodations(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount,
      Role role);
}
