package com.hotelJava.room.util;

import com.hotelJava.room.domain.Room;
import com.hotelJava.room.dto.CreateRoomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

  Room toEntity(CreateRoomRequest createRoomRequest);
}
