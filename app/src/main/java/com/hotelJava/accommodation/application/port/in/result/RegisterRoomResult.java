package com.hotelJava.accommodation.application.port.in.result;

import com.hotelJava.accommodation.domain.Picture;
import java.util.List;
import lombok.Getter;

@Getter
public class RegisterRoomResult {
  private Long id;

  private String name;

  private int price;

  private int maxOccupancy;

  private List<Picture> pictures;
}
