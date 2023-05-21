package com.hotelJava.reservation.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.member.repository.MemberRepository;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.dto.CreateReservationResponseDto;
import com.hotelJava.reservation.repository.ReservationRepository;
import com.hotelJava.reservation.util.RandomReservationNumberGenerator;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class KyungtakReservationService implements ReservationService {

  private final RoomRepository roomRepository;
  private final MemberRepository memberRepository;
  private final ReservationRepository reservationRepository;
  private final RandomReservationNumberGenerator randomReservationNumberGenerator;

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.KYUNGTAK_RESERVATION);
  }

  @Transactional
  public CreateReservationResponseDto createReservation(
      Long roomId, String email, CreateReservationRequestDto dto) {
    Room room =
        roomRepository
            .findById(roomId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.ROOM_NOT_FOUND));
    Member member =
        memberRepository
            .findByEmail(email)
            .orElseThrow(() -> new BadRequestException(ErrorCode.EMAIL_NOT_FOUND));

    // 재고 확인
    if (room.isNotEnoughStockAtCheckDate(dto.getCheckDate())) {
      throw new BadRequestException(ErrorCode.OUT_OF_STOCK);
    }

    // 최대 인원수 확인
    if (room.isOverMaxOccupancy(dto.getNumberOfGuests())) {
      throw new BadRequestException(ErrorCode.OVER_MAX_OCCUPANCY);
    }

    // 재고 -1
    room.calcStock(dto.getCheckDate(), -1);

    // 예약
    String reservationNo = randomReservationNumberGenerator.generateReservationNumber();
    Reservation reservation = new Reservation(member, room, reservationNo, dto, dto.getPaymentType());
    reservationRepository.save(reservation);

    return new CreateReservationResponseDto(reservationNo);
  }
}
