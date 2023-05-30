package com.hotelJava.accommodation.domain;

import com.hotelJava.common.util.BaseTimeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Picture extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private PictureInfo pictureInfo;

  @Enumerated(EnumType.STRING)
  private PictureType pictureType;

  public Picture(PictureInfo pictureInfo) {
    this.pictureInfo = pictureInfo;
  }

  public Picture(PictureInfo pictureInfo, PictureType pictureType) {
    this.pictureInfo = pictureInfo;
    this.pictureType = pictureType;
  }
}
