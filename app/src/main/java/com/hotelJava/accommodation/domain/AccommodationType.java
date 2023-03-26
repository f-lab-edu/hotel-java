package com.hotelJava.accommodation.domain;

public enum AccommodationType {
  MOTEL("모텔"),
  HOTEL_RESORT("호텔/리조트"),
  PENSION("펜션"),
  GUESTHOUSE("게스트하우스"),
  CAMPING_GLAMPING("캠핑/글램핑");

  private final String value;

  AccommodationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
