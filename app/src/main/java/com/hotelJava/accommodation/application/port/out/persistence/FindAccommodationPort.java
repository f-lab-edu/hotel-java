package com.hotelJava.accommodation.application.port.out.persistence;

import com.hotelJava.accommodation.domain.Accommodation;

public interface FindAccommodationPort {
  Accommodation findById(Long id);
}
