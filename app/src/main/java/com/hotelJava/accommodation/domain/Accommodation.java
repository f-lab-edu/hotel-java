package com.hotelJava.accommodation.domain;

import com.hotelJava.common.embeddable.Address;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accommodation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkInTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkOutTime;

    private double rating;

    @Transient
    private String firstPhoneNumber;

    @Transient
    private String secondPhoneNumber;

    @Transient
    private String thirdPhoneNumber;

    private String phoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL) // 연관관계의 주인인 Room.accommodation
    private List<Room> rooms = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AccommodationPicture_id") // 주 테이블에 외래키
    private AccommodationPicture accommodationPicture;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Access(AccessType.PROPERTY)
    public String getPhoneNumber() {
        return firstPhoneNumber + "-" + secondPhoneNumber + "-" + thirdPhoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //== 연관관계 편의 메소드 ==//
    public void addRooms(Room room) {
        this.rooms.add(room);
        room.setAccommodation(this);
    }
}
