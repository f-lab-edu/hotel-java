package com.hotelJava.accommodation.domain.specification;

import com.hotelJava.accommodation.domain.Picture;
import com.hotelJava.common.embeddable.Money;
import java.util.List;

public interface RoomProfile {
  String getName();

  int getMaxOccupancy();

  Money getPrice();

  List<Picture> getPictures();
}
