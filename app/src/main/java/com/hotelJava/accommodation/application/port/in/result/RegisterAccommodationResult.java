package com.hotelJava.accommodation.application.port.in.result;

import com.hotelJava.accommodation.domain.AccommodationType;
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
public class RegisterAccommodationResult {
  private Long id;

  private String name;

  private Address address;

  private AccommodationType type;
}
