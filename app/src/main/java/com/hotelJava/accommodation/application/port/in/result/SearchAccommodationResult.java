package com.hotelJava.accommodation.application.port.in.result;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.common.embeddable.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class SearchAccommodationResult {
  private Long id;

  private String name;

  private double rating;

  private String phoneNumber;

  private Address address;

  private AccommodationType type;

  private Picture picture;

  private String description;

  private long minimumRoomPrice;
}
