package com.hotelJava.reservation.util;

import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

  Reservation toEntity(CreateReservationRequest createReservationRequest);
}
