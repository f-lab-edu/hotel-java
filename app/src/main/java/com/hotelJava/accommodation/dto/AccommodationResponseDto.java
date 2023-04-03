package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.domain.Picture;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.reservation.domain.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AccommodationResponseDto {

  private String name;

  private Address address;

  private AccommodationType type;

  private int price;

  private double rating;

  private String phoneNumber;

  private Picture picture;

  private String description;

  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  // TODO: [Entity => dto] ModelMapper와 생성자 장단점
  public static AccommodationResponseDto of(Accommodation accommodation) {
    return AccommodationResponseDto.builder()
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .type(accommodation.getType())
            .price(0) // TODO: 숙소의 가장 싼 룸의 가격을 가져와야함
            .rating(accommodation.getRating())
            .phoneNumber(accommodation.getPhoneNumber())
            .picture(accommodation.getPicture())
            .description(accommodation.getDescription())
            .status(accommodation.getStatus())
            .build();
  }
}
