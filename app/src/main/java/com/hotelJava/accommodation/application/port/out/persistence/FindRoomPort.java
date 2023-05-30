package com.hotelJava.accommodation.application.port.out.persistence;

import com.hotelJava.accommodation.domain.Room;

public interface FindRoomPort {
  Room findById(Long id);
}
