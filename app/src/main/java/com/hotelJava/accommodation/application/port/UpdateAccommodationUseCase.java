package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.dto.UpdateAccommodationRequestDto;

public interface UpdateAccommodationUseCase {

  void updateAccommodation(
      Long accommodationId, UpdateAccommodationRequestDto updateAccommodationRequestDto);
}
