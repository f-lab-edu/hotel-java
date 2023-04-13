package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationMapper {

  Accommodation toEntity(CreateAccommodationRequestDto createAccommodationRequestDto);

  @Mapping(source = "accommodation.picture", target = "pictureResponseDto")
  AccommodationResponseDto toAccommodationResponseDto(int minimumRoomPrice,
      Accommodation accommodation);
}
