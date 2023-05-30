package com.hotelJava.accommodation.application.port.in.result;

import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.accommodation.domain.specification.RoomProfile;
import com.hotelJava.common.embeddable.Money;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class FindRoomResult implements RoomProfile {
  private String name;
  private Money price;
  private int maxOccupancy;
  private List<Picture> pictures;
}
