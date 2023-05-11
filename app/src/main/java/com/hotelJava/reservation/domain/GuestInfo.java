package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;

public interface GuestInfo {

  String getGuestName();

  String getGuestPhone();

  int getNumberOfGuests();

  CheckDate getCheckDate();
}
