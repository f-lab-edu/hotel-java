package com.hotelJava.reservation.facade;

import com.hotelJava.reservation.application.service.EagerReservationService;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final EagerReservationService eagerReservationService;

    public void createReservation(long roomId, String email, CreateReservationRequest createReservationRequest)
            throws InterruptedException {
        int cnt = 3;

        while (cnt > 0) {
            try {
                eagerReservationService.createReservation(roomId, email, createReservationRequest);

                break; // 정상적으로 수량 감소가 됐다면 while 문 탈출
            } catch (Exception e) {
                cnt--;

                Random random = new Random();
                int randomSleepTime = 200 + random.nextInt(801);

                Thread.sleep(randomSleepTime);
//                Thread.sleep((long) Math.random()); // 수량 감소에 실패하게 된다면 50 millis 대기 후 재시도
            }
        }
    }
}
