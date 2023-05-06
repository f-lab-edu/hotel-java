package com.hotelJava.payment.service;

import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.payment.dto.CreatePaymentRequestDto;
import com.hotelJava.payment.dto.ImpUidDto;
import com.hotelJava.reservation.service.ReservationService;
import com.hotelJava.reservation.service.ReservationServiceManager;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentByKakaoPay implements PaymentService {

  private final ReservationServiceManager reservationServiceManager;

  private final IamportClient api =
      new IamportClient(
          "6318782456272832",
          "drziCrVVErSjpA7mal4EKUNrTuGa1Gf6oG0keXXRO6cs2AVo5YVZGeAuUZsry2YfKtTSVFZFnmcIlWIv");

  @Override
  public IamportResponse<Payment> verifyIamport(ImpUidDto impUidDto) {
    try {
      return api.paymentByImpUid(impUidDto.getImpUid());
    } catch (IamportResponseException | IOException e) {
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @Transactional
  public void savePayment(CreatePaymentRequestDto createPaymentRequestDto) {
    try {
      Payment payment =
          verifyIamport(ImpUidDto.builder().impUid(createPaymentRequestDto.getImpUid()).build())
              .getResponse();

      // 결제된 금액과 클라이언트에서 요청한 금액 비교 검증
      if (payment.getAmount().compareTo(BigDecimal.valueOf(createPaymentRequestDto.getPrice()))
          != 0) {

        // 결제 취소
        api.cancelPaymentByImpUid(new CancelData(createPaymentRequestDto.getImpUid(), true));

        throw new BadRequestException(ErrorCode.BAD_REQUEST_ERROR);
      }

      // DB에 예약정보 저장
      ReservationService reservationService =
          reservationServiceManager.findService(
              createPaymentRequestDto.getCreateReservationRequestDto().getReservationCommand());

      reservationService.saveReservation(createPaymentRequestDto.getCreateReservationRequestDto());

      // DB에 결제정보 저장

    } catch (IamportResponseException | IOException e) {
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
