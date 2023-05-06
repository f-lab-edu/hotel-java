package com.hotelJava.picture.dto;

import com.hotelJava.picture.domain.PictureInfo;
import com.hotelJava.picture.domain.PictureType;
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
public class PictureDto {

  private Long id;

  @Embedded private PictureInfo pictureInfo;

  private PictureType pictureType;
}
