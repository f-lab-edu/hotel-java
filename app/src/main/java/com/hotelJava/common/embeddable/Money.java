package com.hotelJava.common.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
  private long value;

  public static Money of(long value) {
    return new Money(value);
  }

  public long longValue() {
    return value;
  }

  public boolean equals(Money money) {
    return this.value == money.value;
  }
}
