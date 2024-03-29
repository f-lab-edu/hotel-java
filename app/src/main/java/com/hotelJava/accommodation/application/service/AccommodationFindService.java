package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.application.port.FindAccommodationQuery;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.FindAccommodationResponse;
import com.hotelJava.accommodation.util.AccommodationMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.member.domain.Role;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AccommodationFindService implements FindAccommodationQuery {

    private final AccommodationRepository accommodationRepository;

    private final AccommodationMapper accommodationMapper;

    // 요청한 지역에 대한 숙소 정보 조회 (+ 각 숙소에 속한 룸의 가격이 가장 작은 값도 포함)
    @Override
    public List<FindAccommodationResponse> findAccommodations(
            AccommodationType type,
            String firstLocation,
            String secondLocation,
            String name,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int guestCount,
            Role role) {
        ReservationStatus reservationStatus = getReservationStatus(role);

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
                            return accommodationMapper.toFindAccommodationResponse(
                                    minimumRoomPrice, accommodation);
                        })
                .collect(Collectors.toList());
    }

    private ReservationStatus getReservationStatus(Role role) {
        if (role.equals(Role.ADMIN)) {
            return ReservationStatus.RESERVATION_COMPLETED;
        }
        return ReservationStatus.RESERVATION_AVAILABLE;
    }
}
