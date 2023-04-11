package com.hotelJava.room.dto;

import com.hotelJava.accommodation.picture.dto.PictureResponseDto;
import com.hotelJava.common.embeddable.CheckTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
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
public class CreateRoomRequestDto {

  @NotBlank(message = "룸 이름을 입력해주세요.")
  @Length(max = 20, message = "룸 이름은 20자 이내로 입력해주세요.")
  private String name;

  @NotBlank(message = "룸 최대 인원을 입력해주세요.")
  private int maxOccupancy;

  @NotBlank(message = "룸 사진 정보가 없습니다.")
  private List<PictureResponseDto> pictureResponseDtos = new ArrayList<>();

  @NotBlank(message = "체크인, 체크아웃 시간을 선택해주세요.")
  @Future private CheckTime checkTime;
}
