package com.hotelJava.accommodation.domain;

import com.hotelJava.common.uril.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String name;

    private int price;

    private int maxOccupancy;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkInTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkOutTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RoomPicture> roomPictures = new ArrayList<>();

    private boolean status;
}
