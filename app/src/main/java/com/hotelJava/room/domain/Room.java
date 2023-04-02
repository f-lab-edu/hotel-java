package com.hotelJava.room.domain;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.reservation.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int maxOccupancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id") // accommodation_id 외래 키로 연관관계를 맺는다.
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomPicture> roomPictures = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Embedded
    private CheckTime checkTime;
}
