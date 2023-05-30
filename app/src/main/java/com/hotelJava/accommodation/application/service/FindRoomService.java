package com.hotelJava.accommodation.application.service;

import static com.hotelJava.accommodation.util.RoomMapper.ROOM_MAPPER;

import com.hotelJava.accommodation.application.port.in.FindRoomQuery;
import com.hotelJava.accommodation.application.port.out.persistence.FindRoomPort;
import com.hotelJava.accommodation.domain.specification.RoomProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindRoomService implements FindRoomQuery {
  private final FindRoomPort findRoomPort;

  @Override
  public RoomProfile findById(Long id) {
    return ROOM_MAPPER.toRoomProfile(findRoomPort.findById(id));
  }
}
