package com.hotelJava.accommodation.domain;

import com.hotelJava.accommodation.picture.domain.Picture;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
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
  private ReservationStatus status;

  // == 연관관계 편의 메소드 ==//
  public void addRooms(Room room) {
    this.rooms.add(room);
    room.setAccommodation(this);
  }
}
