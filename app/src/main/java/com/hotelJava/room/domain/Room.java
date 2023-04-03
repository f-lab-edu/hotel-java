package com.hotelJava.room.domain;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.domain.Picture;
import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.Reservation;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<Picture> pictures = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Embedded
    private CheckTime checkTime;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations = new ArrayList<>();
}
