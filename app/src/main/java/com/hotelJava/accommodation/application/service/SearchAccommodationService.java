package com.hotelJava.accommodation.application.service;

import static com.hotelJava.accommodation.util.AccommodationMapper.ACCOMMODATION_MAPPER;

import com.hotelJava.accommodation.application.port.in.SearchAccommodationQuery;
import com.hotelJava.accommodation.application.port.in.result.SearchAccommodationResult;
import com.hotelJava.accommodation.application.port.out.persistence.AccommodationSearchCondition;
import com.hotelJava.accommodation.application.port.out.persistence.SearchAccommodationsPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Room;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SearchAccommodationService implements SearchAccommodationQuery {

  private final SearchAccommodationsPort searchAccommodationsPort;

  @Override
  public List<SearchAccommodationResult> search(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount) {

    List<Accommodation> accommodations =
        searchAccommodationsPort.search(
            type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);

    return toSearchedResult(accommodations);
  }

  @Override
  public List<SearchAccommodationResult> search(AccommodationSearchCondition condition) {
    List<Accommodation> accommodations = searchAccommodationsPort.search(condition);

    return toSearchedResult(accommodations);
  }

  private List<SearchAccommodationResult> toSearchedResult(List<Accommodation> accommodations) {
    List<SearchAccommodationResult> result = new ArrayList<>();

    for (Accommodation accommodation : accommodations) {
      Optional<Room> minPriceRoom = accommodation.getMinPriceRoom();
      if (minPriceRoom.isEmpty()) {
        continue;
      }
      result.add(
          ACCOMMODATION_MAPPER.toSearchedResult(
              minPriceRoom.get().getPrice().longValue(), accommodation));
    }

    result.sort(Comparator.comparing(SearchAccommodationResult::getMinimumRoomPrice));

    return result;
  }
}
