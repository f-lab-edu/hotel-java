package com.hotelJava.accommodation.application.port.in;

import com.hotelJava.accommodation.application.port.in.command.AddRoomCommand;

public interface AddRoomUseCase {
  void add(Long accommodationId, AddRoomCommand addRoomCommand);
}
