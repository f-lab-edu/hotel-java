package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String reservationNo;

    private String accommodationName;

    private String roomName;

    @Embedded
    private CheckTime checkTime;

    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
