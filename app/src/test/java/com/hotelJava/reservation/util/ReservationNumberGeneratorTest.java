package com.hotelJava.reservation.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReservationNumberGeneratorTest implements ReservationNumberGenerator{

    @Override
    public String generateReservationNumber() {
        return "test";
    }

    @DisplayName("예약번호 생성 기능 테스트")
    @Test
    void 예약번호_생성() {
        // given

        // when
        String reservationNumber = generateReservationNumber();

        // then
        assertThat(reservationNumber).isEqualTo("test");
    }
}
