package com.hotelJava.accommodation.picture.dto;

import com.hotelJava.accommodation.picture.domain.PictureInfo;
import com.hotelJava.accommodation.picture.domain.PictureType;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PictureResponseDto {

  @Embedded private PictureInfo pictureInfo;

  private PictureType pictureType;
}
