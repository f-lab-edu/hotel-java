package com.hotelJava.room.dto;

import com.hotelJava.common.embeddable.CheckTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateRoomResponseDto {

  private Long id;
  
  private String name;

  private int price;
  
  private int maxOccupancy;

  private CheckTime checkTime;
}
