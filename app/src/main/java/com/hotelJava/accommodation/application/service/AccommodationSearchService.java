package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.application.port.FindAccommodationQuery;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.FindAccommodationResponseDto;
import com.hotelJava.accommodation.util.AccommodationMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.room.domain.Room;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AccommodationSearchService implements FindAccommodationQuery {

    private final AccommodationRepository accommodationRepository;

    private final AccommodationMapper accommodationMapper;

    public List<FindAccommodationResponseDto> findAccommodations(
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
                            return accommodationMapper.toFindAccommodationResponseDto(
                                    minimumRoomPrice, accommodation);
                        })
                .collect(Collectors.toList());
    }
}
