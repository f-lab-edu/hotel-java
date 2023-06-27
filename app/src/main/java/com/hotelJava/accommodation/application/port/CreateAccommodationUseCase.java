package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.dto.CreateAccommodationRequest;
import com.hotelJava.accommodation.dto.CreateAccommodationResponse;

public interface CreateAccommodationUseCase {

  CreateAccommodationResponse createAccommodation(CreateAccommodationRequest command);
}
