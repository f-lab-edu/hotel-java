package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.picture.domain.Picture;
import com.hotelJava.common.embeddable.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Embedded private Address address;

  @Enumerated(EnumType.STRING)
  private AccommodationType type;

  private double rating;

  private int minimumRoomPrice;

  private String phoneNumber;

  private Picture picture;

  private String description;
}
