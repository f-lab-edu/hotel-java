package com.hotelJava.inventory;

import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Inventory extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  private LocalDate date;

  private long quantity;

  public Inventory(LocalDate date, long quantity) {
    this.date = date;
    this.quantity = quantity;
  }

  public boolean isEnoughQuantity() {
    if (quantity <= 0) {
      log.error("quantity at {} is 0", date);
      return false;
    }
    return true;
  }

  public void validate() {
    if (!isEnoughQuantity()) {
      throw new BadRequestException(ErrorCode.OUT_OF_STOCK);
    }
  }

  public Inventory reduceQuantity(CheckDate checkDate) {
    if (checkDate.matches(date)) {
      validate();
      return new Inventory(date, quantity - 1);
    }
    return this;
  }
}
