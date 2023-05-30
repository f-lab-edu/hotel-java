package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.application.port.in.UpdateAccommodationUseCase;
import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateAccommodationService implements UpdateAccommodationUseCase {

  private final FindAccommodationPort findAccommodationPort;

  @Override
  public void updateAccommodation(
      Long accommodationId, UpdateAccommodationCommand updateAccommodationCommand) {

    Accommodation accommodation = findAccommodationPort.findById(accommodationId);

    accommodation.updateAccommodation(updateAccommodationCommand);
  }
}
