package com.hotelJava.accommodation.domain;

import com.hotelJava.accommodation.domain.specification.RoomProfile;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.Reservation;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room extends BaseTimeEntity implements RoomProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price"))
  private Money price;

  private int maxOccupancy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id") // accommodation_id 외래 키로 연관관계를 맺는다.
  private Accommodation accommodation;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id")
  @Default
  private List<Picture> pictures = new ArrayList<>();

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @Default
  private List<Reservation> reservations = new ArrayList<>();

  @Default
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  @MapKey(name = "date")
  private Map<LocalDate, Stock> stocks = new ConcurrentHashMap<>();

  public void calcStock(CheckDate checkDate, int value) {
    stocks.values().stream()
        .filter(stock -> checkDate.matches(stock.getDate()))
        .forEach(stock -> stock.calcQuantity(value));
  }

  public boolean isEnoughStockAtCheckDate(CheckDate checkDate) {
    checkDate.duration().forEach(localDate -> log.info(localDate.toString()));
    return checkDate
        .duration()
        .allMatch(
            localDate -> stocks.containsKey(localDate) && !stocks.get(localDate).isZeroQuantity());
  }

  public boolean isNotEnoughStockAtCheckDate(CheckDate checkDate) {
    return !isEnoughStockAtCheckDate(checkDate);
  }

  public boolean isOverMaxOccupancy(int guestNumber) {
    return guestNumber > maxOccupancy;
  }

  public long calcPrice() {
    return price.longValue();
  }

  // == 연관관계 편의 메소드 ==//
  public void setAccommodation(Accommodation accommodation) {
    this.accommodation = accommodation;
  }

  public void addPicture(Picture picture) {
    pictures.add(picture);
  }

  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
    reservation.setRoom(this);
  }

  public void addStock(Stock stock) {
    stocks.put(stock.getDate(), stock);
    stock.setRoom(this);
  }
}
