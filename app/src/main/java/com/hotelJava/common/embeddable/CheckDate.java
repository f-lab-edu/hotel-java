package com.hotelJava.common.embeddable;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheckDate {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate checkInDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate checkOutDate;

  public boolean matches(LocalDate date) {
    return date.isEqual(checkInDate) || (date.isAfter(checkInDate) && date.isBefore(checkOutDate));
  }
}
