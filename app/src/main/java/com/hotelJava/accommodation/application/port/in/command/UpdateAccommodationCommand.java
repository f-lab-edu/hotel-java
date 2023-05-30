package com.hotelJava.accommodation.application.port.in.command;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.common.embeddable.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateAccommodationCommand {
  @NotBlank(message = "숙소 이름을 입력해주세요.")
  @Length(max = 20, message = "숙소 이름은 20자 이내로 입력해주세요.")
  private String name;

  @NotBlank(message = "숙소 번호의 형식이 맞지 않습니다.")
  @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
  private String phoneNumber;

  @NotNull(message = "숙소 타입을 선택해주세요.")
  private AccommodationType type;

  @NotNull(message = "주소를 입력해주세요.")
  private Address address;

  @NotNull(message = "숙소 사진 정보가 없습니다.")
  private Picture picture;

  private String description;
}
