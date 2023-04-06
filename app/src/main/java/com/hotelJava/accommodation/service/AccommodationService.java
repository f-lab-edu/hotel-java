package com.hotelJava.accommodation.service;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import com.hotelJava.accommodation.repository.AccommodationRepository;
import com.hotelJava.accommodation.util.AccommodationMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.hotelJava.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

  private final AccommodationRepository accommodationRepository;

  private final AccommodationMapper accommodationMapper;

  public List<AccommodationResponseDto> findAccommodations(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount) {
    List<Accommodation> accommodations =
        accommodationRepository.findAccommodations(
            type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);

    List<AccommodationResponseDto> accommodationResponseDtos = new ArrayList<>();

    for (Accommodation accommodation : accommodations) {
      int minimumRoomPrice = Integer.MAX_VALUE;

      for (Room room : accommodation.getRooms()) {
        minimumRoomPrice = Math.min(minimumRoomPrice, room.getPrice());
      }
      AccommodationResponseDto accommodationResponseDto =
          accommodationMapper.toAccommodationResponseDto(accommodation);
      accommodationResponseDto.setMinimumRoomPrice(minimumRoomPrice);
      accommodationResponseDtos.add(accommodationResponseDto);
    }

    return accommodationResponseDtos;
  }
}
