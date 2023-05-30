package com.hotelJava.reservation.application.service;

import static com.hotelJava.reservation.util.ReservationMapper.RESERVATION_MAPPER;

import com.hotelJava.accommodation.application.port.out.persistence.FindRoomPort;
import com.hotelJava.accommodation.domain.Room;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class EagerReservationHandler implements ReservationHandler, ConfirmHandler {

  private final FindRoomPort findRoomPort;
  private final FindMemberPort findMemberPort;
  private final SaveReservationPort saveReservationPort;
  private final RandomReservationNumberGenerator randomReservationNumberGenerator;
  private final VerifyPaymentAmountPort verifyPaymentAmountPort;
  private final CancelPaymentPort cancelPaymentPort;

  @Override
  public boolean supports(ReserveType reservationCommand) {
    return reservationCommand == ReserveType.EAGER;
  }

  @Transactional
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

    preConfirmReservation.consumeStock();

    return RESERVATION_MAPPER.toReserveResult(saveReservationPort.save(preConfirmReservation));
  }

  @Override
  public Optional<ConfirmedReservationHistory> confirm(
      Reservation reservation, String paymentApiNo) {

    if (verifyPaymentAmountPort.verify(paymentApiNo, reservation.getPayment().getAmount().longValue())) {
      reservation.confirm();
      return Optional.of(RESERVATION_MAPPER.toConfirmedReservationHistory(reservation));
    }

    cancel(reservation, paymentApiNo);
    return Optional.empty();
  }

  private boolean cancel(Reservation reservation, String paymentApiNo) {
    reservation.restoreStock();
    return cancelPaymentPort.cancel(paymentApiNo);
  }
}
