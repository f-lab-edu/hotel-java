package com.hotelJava.accommodation.application.port.in;

import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.result.RegisterAccommodationResult;

public interface RegisterAccommodationUseCase {

  RegisterAccommodationResult register(RegisterAccommodationCommand command);
}
