package com.hotelJava.reservation.domain;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReservationValidator {
  public static void validate(Reservation reservation) {
    if (reservation.isDeleted()) {
      log.info("삭제처리된 예약입니다.");
      throw new BadRequestException(ErrorCode.RESERVATION_NOT_FOUND);
    }

    if (reservation.isAlreadyConfirmed()) {
      log.info("이미 확정된 예약입니다.");
      throw new BadRequestException(ErrorCode.RESERVATION_NOT_FOUND);
    }

    if (reservation.isExpiredPayment()) {
      log.info("결제 시간이 만료되었습니다.");
      throw new BadRequestException(ErrorCode.RESERVATION_NOT_FOUND);
    }

    if (reservation.isNotEnoughStockAtCheckDate()) {
      log.info("room's stock is not enough. date = {}", reservation.getCheckDate());
      throw new BadRequestException(ErrorCode.OUT_OF_STOCK);
    }

    if (reservation.isOverMaxOccupancy()) {
      log.info(
          "room's maxOccupancy(={}) < guest number(={})",
          reservation.getRoom().getMaxOccupancy(),
          reservation.getNumberOfGuests());
      throw new BadRequestException(ErrorCode.OVER_MAX_OCCUPANCY);
    }
  }
}
