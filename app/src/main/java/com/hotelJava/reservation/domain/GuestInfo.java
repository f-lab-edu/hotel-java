package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;

public interface GuestInfo {

  String getName();

  String getPhone();

  int getNumberOfGuests();

  CheckDate getCheckDate();
}
