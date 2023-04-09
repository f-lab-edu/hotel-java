package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.picture.dto.PictureResponseDto;
import com.hotelJava.common.embeddable.Address;
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
public class AccommodationResponseDto {

  private String name;

  private Address address;

  private AccommodationType type;

  private double rating;

  private int minimumRoomPrice;

  private String phoneNumber;

  private PictureResponseDto pictureResponseDto;

  private String description;
}
