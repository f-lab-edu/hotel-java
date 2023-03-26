package com.hotelJava.reservation.domain;

import com.hotelJava.common.uril.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String number;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "Member_id")
//    private Member member;

    //== 예약 번호 생성 ==//
    public void generateReservationNumber() {
        this.number = UUID.randomUUID().toString() + LocalDateTime.now();
    }
}
