package com.hotelJava.room.domain;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.inventory.Inventory;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.GuestInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class Room extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int price;

  private int maxOccupancy;

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
  private List<Inventory> inventories = new ArrayList<>();

  public Reservation reserve(GuestInfo info) {
    Reservation reservation = new Reservation(info);
    reservations.add(reservation);
    return reservation;
  }

  public List<Inventory> reduceStock(CheckDate checkDate) {
    return inventories.stream()
        .map(item -> item.reduceQuantity(checkDate))
        .collect(Collectors.toList());
  }

  public boolean isAvailableReservation(int numberOfGuests, CheckDate checkDate) {
    if (!isLowerMaxOccupancy(numberOfGuests)) {
      log.info("numberOfGuests({}) is over maxOccupancy({})", numberOfGuests, maxOccupancy);
      return false;
    }
    return isStockEnough(checkDate);
  }

  public boolean isStockEnough(CheckDate checkDate) {
    return inventories.stream()
        .filter(i -> checkDate.matches(i.getDate()))
        .allMatch(Inventory::isEnoughQuantity);
  }

  public boolean isLowerMaxOccupancy(int guestNumber) {
    return guestNumber <= maxOccupancy;
  }

  public int calcPrice() {
    return price;
  }

  // == 연관관계 편의 메소드 ==//
  public void addPicture(Picture picture) {
    pictures.add(picture);
    picture.setRoom(this);
  }

  public void addReservation(Reservation reservation) {
    reservations.add(reservation);
    reservation.setRoom(this);
  }

  public void addInventory(Inventory inventory) {
    inventories.add(inventory);
    inventory.setRoom(this);
  }
}
