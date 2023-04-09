package com.hotelJava.accommodation.service;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import com.hotelJava.accommodation.repository.AccommodationRepository;
import com.hotelJava.accommodation.util.AccommodationMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
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
    return accommodations.stream()
        .map(
            accommodation -> {
              int minimumRoomPrice =
                  accommodation.getRooms().stream()
                      .mapToInt(Room::getPrice)
                      .min()
                      .orElseThrow(
                          () -> new InternalServerException(ErrorCode.NO_MINIMUM_PRICE_FOUND));
              return accommodationMapper.toAccommodationResponseDto(
                  minimumRoomPrice, accommodation);
            })
        .collect(Collectors.toList());
  }
}
