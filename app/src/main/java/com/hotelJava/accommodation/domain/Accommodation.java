package com.hotelJava.accommodation.domain;

import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.domain.PictureType;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accommodation extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private AccommodationType type;

  private double rating;

  private String phoneNumber;

  @Embedded private Address address;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL) // 연관관계의 주인인 Room.accommodation
  @Builder.Default
  private List<Room> rooms = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id") // 주 테이블에 외래키
  private Picture picture;

  @Lob private String description;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private ReservationStatus status = ReservationStatus.RESERVATION_AVAILABLE;

  public Accommodation updateAccommodation(
      String name,
      AccommodationType type,
      String phoneNumber,
      Address address,
      Picture picture,
      String description) {
    this.name = name;
    this.type = type;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.picture = picture;
    this.description = description;

    return this;
  }

  // == 연관관계 편의 메소드 ==//
  public void setPicture(Picture picture) {
    this.picture = picture;
    picture.setPictureType(PictureType.ACCOMMODATION);
    picture.setAccommodation(this);
  }

  public void createAccommodation(List<Room> rooms, Picture accommodationPicture) {
    setPicture(accommodationPicture);
    this.rooms.addAll(rooms);
    rooms.forEach(room -> room.setAccommodation(this));
  }
}
