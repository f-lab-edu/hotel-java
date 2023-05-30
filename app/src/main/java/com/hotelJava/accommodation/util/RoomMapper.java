package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.application.port.in.command.AddRoomCommand;
import com.hotelJava.accommodation.application.port.in.result.FindRoomResult;
import com.hotelJava.accommodation.domain.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
  RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

  @Mapping(source = "addRoomCommand.price", target = "price")
  Room toEntity(AddRoomCommand addRoomCommand);

  @Mapping(source = "room.price", target = "price")
  FindRoomResult toRoomProfile(Room room);
}
