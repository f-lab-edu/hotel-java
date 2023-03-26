package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
public class AccommodationRequestDto {

  @NotBlank(message = "숙소 이름을 입력해주세요.")
  @Length(max = 20, message = "숙소 이름은 20자 이내로 입력해주세요.")
  private String name;

  private AccommodationType accommodationType;

  @Future
  private LocalDateTime checkInDate;

  @Future
  private LocalDateTime checkOutDate;

  private String guestCount;
}
