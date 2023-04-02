package com.hotelJava.reservation.domain;

import com.hotelJava.common.embeddable.CheckTime;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.member.domain.Member;
import com.hotelJava.payment.domain.PaymentType;
import jakarta.persistence.*;
import lombok.*;

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

    private String name;

    private String reservationNo;

    private String accommodationName;

    private String roomName;

    @Embedded
    private CheckTime checkTime;

    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Transient
    private String firstPhoneNumber;

    @Transient
    private String secondPhoneNumber;

    @Transient
    private String thirdPhoneNumber;

    private String phoneNumber;

    @Access(AccessType.PROPERTY)
    public String getPhoneNumber() {
        return firstPhoneNumber + "-" + secondPhoneNumber + "-" + thirdPhoneNumber;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 예약 번호 생성 ==//
    public void generateReservationNumber() {
        this.reservationNo = UUID.randomUUID().toString() + LocalDateTime.now();
    }
}
