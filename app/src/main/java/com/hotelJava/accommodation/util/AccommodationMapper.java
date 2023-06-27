package com.hotelJava.accommodation.util;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.CreateAccommodationRequest;
import com.hotelJava.accommodation.dto.CreateAccommodationResponse;
import com.hotelJava.accommodation.dto.FindAccommodationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationMapper {

  Accommodation toEntity(CreateAccommodationRequest createAccommodationRequest);

  @Mapping(source = "accommodation.picture", target = "pictureDto")
  @Mapping(source = "accommodation.rooms", target = "createRoomResponses")
  CreateAccommodationResponse toCreateAccommodationResponse(Accommodation accommodation);

  @Mapping(source = "accommodation.picture", target = "pictureDto")
  FindAccommodationResponse toFindAccommodationResponse(
      int minimumRoomPrice, Accommodation accommodation);
}
