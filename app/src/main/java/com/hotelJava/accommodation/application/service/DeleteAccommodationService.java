package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.application.port.in.DeleteAccommodationUseCase;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteAccommodationService implements DeleteAccommodationUseCase {
  private final FindAccommodationPort findAccommodationPort;

  @Override
  public boolean deleteAccommodation(Long id) {
    Accommodation accommodation = findAccommodationPort.findById(id);
    return accommodation.delete();
  }
}
