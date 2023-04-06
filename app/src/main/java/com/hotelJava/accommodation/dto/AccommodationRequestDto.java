package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccommodationRequestDto {

  @NotBlank(message = "숙소 이름을 입력해주세요.")
  @Length(max = 20, message = "숙소 이름은 20자 이내로 입력해주세요.")
  private String name;

  @Enumerated(EnumType.STRING)
  private AccommodationType accommodationType;

  @Embedded private Address address;

  @Future private LocalDate checkInDate;

  @Future private LocalDate checkOutDate;

  private int guestCount;
}
