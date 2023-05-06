package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;

public interface SaveAccommodationUseCase {

  CreateAccommodationResponseDto saveAccommodation(CreateAccommodationRequestDto command);
}
