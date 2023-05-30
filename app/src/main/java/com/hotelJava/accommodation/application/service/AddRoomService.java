package com.hotelJava.accommodation.application.service;

import static com.hotelJava.accommodation.util.RoomMapper.ROOM_MAPPER;

import com.hotelJava.accommodation.application.port.in.AddRoomUseCase;
import com.hotelJava.accommodation.application.port.in.command.AddRoomCommand;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddRoomService implements AddRoomUseCase {

  private final FindAccommodationPort findAccommodationPort;

  @Override
  public void add(Long accommodationId, AddRoomCommand addRoomCommand) {
    Accommodation accommodation = findAccommodationPort.findById(accommodationId);
    accommodation.addRoom(ROOM_MAPPER.toEntity(addRoomCommand));
  }
}
