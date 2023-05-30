package com.hotelJava.reservation.application.service;

import static com.hotelJava.reservation.util.ReservationMapper.RESERVATION_MAPPER;

import com.hotelJava.accommodation.application.port.out.persistence.FindRoomPort;
import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
import com.hotelJava.reservation.application.port.in.result.ConfirmedReservationHistory;
import com.hotelJava.reservation.application.port.in.result.ReserveResult;
import com.hotelJava.reservation.application.port.out.api.CancelPaymentPort;
import com.hotelJava.reservation.application.port.out.api.VerifyPaymentAmountPort;
import com.hotelJava.reservation.application.port.out.persistence.SaveReservationPort;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationValidator;
import com.hotelJava.reservation.domain.ReserveType;
import com.hotelJava.reservation.util.RandomReservationNumberGenerator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LazyReservationHandler implements ReservationHandler, ConfirmHandler {

  private final FindRoomPort findRoomPort;
  private final FindMemberPort findMemberPort;
  private final SaveReservationPort saveReservationPort;
  private final RandomReservationNumberGenerator randomReservationNumberGenerator;
  private final VerifyPaymentAmountPort verifyPaymentAmountPort;
  private final CancelPaymentPort cancelPaymentPort;

  @Override
  public boolean supports(ReserveType reserveType) {
    return reserveType == ReserveType.LAZY;
  }

  @Override
  public ReserveResult reserve(String email, Long roomId, ReserveCommand reserveCommand) {
    Room room = findRoomPort.findById(roomId);

    Member member = findMemberPort.findByEmail(email);

    Reservation preConfirmReservation =
        new Reservation(
            member,
            room,
            randomReservationNumberGenerator.generateReservationNumber(),
            reserveCommand);

    ReservationValidator.validate(preConfirmReservation);

    return RESERVATION_MAPPER.toReserveResult(saveReservationPort.save(preConfirmReservation));
  }

  @Override
  public Optional<ConfirmedReservationHistory> confirm(
      Reservation reservation, String paymentApiNo) {

    try {
      ReservationValidator.validate(reservation);
    } catch (BadRequestException e) {
      cancel(paymentApiNo);
      return Optional.empty();
    }

    if (verifyPaymentAmountPort.verify(
        reservation.getReservationNo(), reservation.getPayment().getAmount().longValue())) {
      reservation.consumeStock();
      reservation.confirm();
      return Optional.of(RESERVATION_MAPPER.toConfirmedReservationHistory(reservation));
    }

    return Optional.empty();
  }

  private boolean cancel(String paymentApiNo) {
    return cancelPaymentPort.cancel(paymentApiNo);
  }
}
