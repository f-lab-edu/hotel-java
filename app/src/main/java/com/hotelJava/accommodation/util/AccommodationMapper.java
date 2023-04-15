package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;
import com.hotelJava.accommodation.dto.FindAccommodationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationMapper {

  Accommodation toEntity(CreateAccommodationRequestDto createAccommodationRequestDto);

  @Mapping(source = "accommodation.picture", target = "pictureDto")
  @Mapping(source = "accommodation.rooms", target = "createRoomResponseDtos")
  CreateAccommodationResponseDto toCreateAccommodationResponseDto(Accommodation accommodation);

  @Mapping(source = "accommodation.picture", target = "pictureDto")
  FindAccommodationResponseDto toFindAccommodationResponseDto(
      int minimumRoomPrice, Accommodation accommodation);
}
