package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.AccommodationRequestDto;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationMapper {

  Accommodation toEntity(AccommodationRequestDto accommodationRequestDto);

  AccommodationResponseDto toAccommodationResponseDto(Accommodation accommodation);
}
