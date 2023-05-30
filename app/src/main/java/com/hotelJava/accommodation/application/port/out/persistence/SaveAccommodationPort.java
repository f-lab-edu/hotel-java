package com.hotelJava.accommodation.application.port.out.persistence;

import com.hotelJava.accommodation.domain.Accommodation;
import java.util.List;

public interface SaveAccommodationPort {
  Accommodation save(Accommodation accommodation);

  List<Accommodation> saveAll(List<Accommodation> accommodations);
}
