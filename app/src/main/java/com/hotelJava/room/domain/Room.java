package com.hotelJava.room.domain;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.stock.domain.Stock;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class Room extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int price;

  private int maxOccupancy;

  private LocalDateTime stockBatchDateTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id") // accommodation_id 외래 키로 연관관계를 맺는다.
  private Accommodation accommodation;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Picture> pictures = new ArrayList<>();

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Reservation> reservations = new ArrayList<>();

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Stock> stocks = new ArrayList<>();

  public void calcStock(CheckDate checkDate, int value) {
    stocks.stream()
        .filter(i -> checkDate.matches(i.getDate()))
        .forEach(i -> i.calcQuantity(value));
  }

  public boolean isNotEnoughStockAtCheckDate(CheckDate checkDate) {
    return stocks.stream()
            .filter(i -> checkDate.matches(i.getDate()))
            .anyMatch(Stock::isZeroQuantity);
  }

  public boolean isOverMaxOccupancy(int guestNumber) {
    return guestNumber > maxOccupancy;
  }

  public int calcPrice() {
    return price;
  }

  public void changeStockBatchDateTime(LocalDateTime now) {
    this.stockBatchDateTime = now;
  }

  //== 연관관계 편의 메소드 ==//
  public void setAccommodation(Accommodation accommodation) {
    this.accommodation = accommodation;
  }

  public void addPicture(Picture picture) {
    pictures.add(picture);
    picture.setRoom(this);
  }

  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
    reservation.setRoom(this);
  }

  public void addStock(Stock stock) {
    stocks.add(stock);
    stock.setRoom(this);
  }
}
