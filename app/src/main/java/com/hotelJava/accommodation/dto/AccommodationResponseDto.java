package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AccommodationResponseDto {

  private String name;

  private String address;

  private String location;

  private AccommodationType type;

  private int shortPrice;

  private int longPrice;

  private double rating;

  private String phoneNumber;

  private MultipartFile picture;

  private String description;
}
