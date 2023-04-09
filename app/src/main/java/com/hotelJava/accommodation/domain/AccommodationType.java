package com.hotelJava.accommodation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccommodationType {

  MOTEL("모텔"),
  HOTEL_RESORT("호텔/리조트"),
  PENSION("펜션"),
  GUESTHOUSE("게스트하우스"),
  CAMPING_GLAMPING("캠핑/글램핑");

  private final String label;
}
