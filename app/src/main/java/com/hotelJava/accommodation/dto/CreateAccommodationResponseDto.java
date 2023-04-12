package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.picture.dto.PictureDto;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.room.dto.CreateRoomResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class CreateAccommodationResponseDto {

  private Long id;

  private String name;

  private double rating;

  private String phoneNumber;

  private Address address;

  private AccommodationType type;

  private List<CreateRoomResponseDto> createRoomResponseDtos;

  private PictureDto pictureDto;

  private String description;
}
