package com.hotelJava.payment.application.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.payment.application.port.CreatePaymentUseCase;
import com.hotelJava.payment.dto.CreatePaymentRequest;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationStatus;
import com.hotelJava.reservation.adapter.persistence.ReservationRepository;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.adapter.persistence.RoomRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentByKakaoPay implements CreatePaymentUseCase {

  private final RoomRepository roomRepository;
  private final ReservationRepository reservationRepository;
  private final IamportClient api;

  @Transactional(noRollbackFor = {BadRequestException.class})
  @Override
  public void createPayment(Long roomId, CreatePaymentRequest dto) {
    Room room =
        roomRepository
            .findById(roomId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.ROOM_NOT_FOUND));
    Reservation reservation =
        reservationRepository
            .findByReservationNo(dto.getReservationNo())
            .orElseThrow(() -> new BadRequestException(ErrorCode.RESERVATION_NOT_FOUND));
    String impUid = dto.getImpUid();

    // 결제 금액 검증
    if (verifyAmount(impUid, dto.getAmount(), room)) {

      // 결제
      reservation.getPayment().changePaymentStatus(dto.getAmount());

      // 예약 처리상태 변경 (대기 -> 완료)
      reservation.changeReservationStatus(ReservationStatus.RESERVATION_COMPLETED);

      return;
    }

    // 예약 및 결제 취소
    cancelReservationAndPayment(impUid, room, reservation);
  }

  private boolean verifyAmount(
      String impUid, int clientRoomAmount, Room room) {
    try {
      IamportResponse<Payment> paymentIamportResponse = api.paymentByImpUid(impUid);
      int iamportRoomAmount = paymentIamportResponse.getResponse().getAmount().intValue();

      if (iamportRoomAmount != clientRoomAmount || iamportRoomAmount != room.getPrice()) {
        log.info("결제 금액이 일치하지 않음");

        return false;
      }
    } catch (IamportResponseException e) {
      log.error("IamportResponseException: {}", e.getMessage());

      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    } catch (IOException e) {
      log.error("IOException: {}", e.getMessage());

      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    return true;
  }

  private void cancelReservationAndPayment(String impUid, Room room, Reservation reservation) {
    try {
      // Iamport 결제 취소
      cancelPayment(impUid);

      // 재고 +1
      room.calcStock(reservation.getCheckDate(), 1);

      // 예약 처리상태 변경 (대기 -> 취소)
      reservation.changeReservationStatus(ReservationStatus.RESERVATION_CANCEL);

      throw new BadRequestException(ErrorCode.PAYMENT_FAIL);
    } catch (IamportResponseException e) {
      log.error("IamportResponseException: {}", e.getMessage());

      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    } catch (IOException e) {
      log.error("IOException: {}", e.getMessage());

      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private void cancelPayment(String impUid) throws IamportResponseException, IOException {
    api.cancelPaymentByImpUid(new CancelData(impUid, true));
  }
}
