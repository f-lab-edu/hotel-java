package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.member.domain.Member;

public interface GuestInfo {
  Member getMember();

  int getNumberOfGuests();

  CheckDate getCheckDate();
}
