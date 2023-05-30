package com.hotelJava.accommodation.application.port.in;

import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;

public interface UpdateAccommodationUseCase {

  void updateAccommodation(
      Long accommodationId, UpdateAccommodationCommand updateAccommodationCommand);
}
