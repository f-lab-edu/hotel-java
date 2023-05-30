package com.hotelJava.accommodation.adapter.out.persistence;

import com.hotelJava.accommodation.application.port.out.persistence.FindRoomPort;
import com.hotelJava.accommodation.domain.Room;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoomAdapter implements FindRoomPort {

  private final RoomRepository roomRepository;

  @Override
  public Room findById(Long id) {
    return roomRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException(ErrorCode.ROOM_NOT_FOUND));
  }
}
