package com.hotelJava.accommodation.application.port.in.command;

import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.accommodation.domain.specification.RoomProfile;
import com.hotelJava.common.embeddable.Money;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddRoomCommand implements RoomProfile {

  @NotBlank(message = "객실 이름을 입력해주세요.")
  @Length(max = 20, message = "룸 이름은 20자 이내로 입력해주세요.")
  private String name;

  @NotNull(message = "가격을 입력하세요.")
  private Money price;

  @Positive(message = "최대 인원을 입력해주세요.")
  private int maxOccupancy;

  @NotEmpty(message = "객실 사진을 첨부하세요.")
  @Default
  private List<Picture> pictures = new ArrayList<>();
}
