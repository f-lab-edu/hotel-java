package com.hotelJava.accommodation.application.port.in;

import com.hotelJava.accommodation.domain.specification.RoomProfile;

public interface FindRoomQuery {
  RoomProfile findById(Long id);
}
