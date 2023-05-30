package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.result.RegisterAccommodationResult;
import com.hotelJava.accommodation.application.port.in.result.SearchAccommodationResult;
import com.hotelJava.accommodation.domain.Accommodation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationMapper {

  AccommodationMapper ACCOMMODATION_MAPPER = Mappers.getMapper(AccommodationMapper.class);

  Accommodation toEntity(RegisterAccommodationCommand registerAccommodationCommand);

  RegisterAccommodationResult toRegisteredResult(Accommodation accommodation);

  SearchAccommodationResult toSearchedResult(long minimumRoomPrice, Accommodation accommodation);
}
