package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.dto.UpdateAccommodationRequest;

public interface UpdateAccommodationUseCase {

  void updateAccommodation(
      Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest);
}
