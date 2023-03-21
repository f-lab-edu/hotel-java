package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AccommodationCreateDto {

  //    @NotBlank(message = "숙소 이름을 입력해주세요.")
  private String name;

  private String address;

  private String location;

  private AccommodationType accommodationType;

  private String phoneNumber;

  private MultipartFile picture;

  private String description;
}
