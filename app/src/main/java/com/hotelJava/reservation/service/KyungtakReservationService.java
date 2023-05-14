package com.hotelJava.reservation.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.repository.ReservationRepository;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KyungtakReservationService implements ReservationService {

  private final RoomRepository roomRepository;

  private final ReservationRepository reservationRepository;

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.KYUNGTAK_RESERVATION);
  }

  @Transactional
  public void createReservation(Long roomId, Member member, CreateReservationRequestDto dto) {
    Room room =
        roomRepository
            .findById(roomId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.ROOM_NOT_FOUND));

    // 재고 확인
    if (room.isNotEnoughInventoryAtCheckDate(dto.getCheckDate())) {
      throw new BadRequestException(ErrorCode.OUT_OF_STOCK);
    }

    // 최대 인원수 확인
    if (room.isOverMaxOccupancy(dto.getNumberOfGuests())) {
      throw new BadRequestException(ErrorCode.OVER_MAX_OCCUPANCY);
    }

    // 재고 -1
    room.calcInventory(dto.getCheckDate(), -1);

    // 예약
    Reservation reservation = new Reservation(member, room, dto.getReservationNo(), dto);
    reservationRepository.save(reservation);
  }
}
