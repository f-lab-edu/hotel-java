package com.hotelJava.accommodation.domain;

import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.ReservationStatus;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE accommodation SET deleted = true WHERE id = ?")
@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
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

  @Lob private String description;

  private boolean deleted;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id")
  private Picture picture;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL) // 연관관계의 주인인 Room.accommodation
  @Default
  private List<Room> rooms = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Default
  private ReservationStatus status = ReservationStatus.RESERVATION_AVAILABLE;

  public Accommodation updateAccommodation(UpdateAccommodationCommand updateAccommodationCommand) {
    name = updateAccommodationCommand.getName();
    type = updateAccommodationCommand.getType();
    phoneNumber = updateAccommodationCommand.getPhoneNumber();
    address = updateAccommodationCommand.getAddress();
    description = updateAccommodationCommand.getDescription();
    picture = updateAccommodationCommand.getPicture();
    return this;
  }

  public Optional<Room> getMinPriceRoom() {
    return rooms.stream().min(Comparator.comparing(room -> room.getPrice().longValue()));
  }

  public boolean delete() {
    if (isDeleted()) {
      return false;
    }
    return deleted = true;
  }

  // == 연관관계 편의 메소드 ==//
  public void addRoom(Room... room) {
    List<Room> addedRooms = List.of(room);
    this.rooms.addAll(addedRooms);
    addedRooms.forEach(r -> r.setAccommodation(this));
  }
}
