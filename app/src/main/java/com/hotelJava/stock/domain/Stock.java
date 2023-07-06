package com.hotelJava.stock.domain;

import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.room.domain.Room;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "room_id")
  private Room room;

  private LocalDate date;

  private long quantity;

  public Stock(LocalDate date, long quantity) {
    this.date = date;
    this.quantity = quantity;
  }

  public boolean isZeroQuantity() {
    if (quantity <= 0) {
      log.info("quantity at {} is 0", date);
      return true;
    }
    return false;
  }

  public void calcQuantity(int value) {
    quantity += value;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }
}
