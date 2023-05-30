package com.hotelJava.reservation.util;

import com.hotelJava.reservation.application.port.in.result.ConfirmedReservationHistory;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;
import com.hotelJava.reservation.domain.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

  ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

  ReserveResult toReserveResult(Reservation reservation);

  @Mapping(source = "reservation.room.name", target = "roomName")
  @Mapping(source = "reservation.payment.paymentStatus", target = "paymentStatus")
  @Mapping(source = "reservation.payment.paymentDate", target = "paymentDate")
  @Mapping(source = "reservation.payment.amount", target = "amount")
  ConfirmedReservationHistory toConfirmedReservationHistory(Reservation reservation);
}
