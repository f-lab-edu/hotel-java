package com.hotelJava.accommodation.adapter.out.persistence;

import com.hotelJava.accommodation.application.port.out.persistence.CheckDuplicateAccommodationPort;
import com.hotelJava.accommodation.application.port.out.persistence.SaveAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegisterAccommodationAdapter
    implements CheckDuplicateAccommodationPort, SaveAccommodationPort {

  private final AccommodationRepository repository;

  @Override
  public Accommodation save(Accommodation accommodation) {
    return repository.save(accommodation);
  }

  @Override
  public List<Accommodation> saveAll(List<Accommodation> accommodation) {
    return repository.saveAll(accommodation);
  }

  @Override
  public boolean isDuplicateByName(String name) {
    return repository.existsByName(name);
  }
}
