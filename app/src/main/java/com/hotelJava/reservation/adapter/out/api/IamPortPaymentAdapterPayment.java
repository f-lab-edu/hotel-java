package com.hotelJava.reservation.adapter.out.api;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class IamPortPaymentAdapterPayment implements PaymentAdapter {

  private final IamportClient api;

  @Override
  public boolean cancel(String paymentApiId) {
    IamportResponse<Payment> iamportResponse = null;
    try {
      iamportResponse = api.cancelPaymentByImpUid(new CancelData(paymentApiId, true));
    } catch (IamportResponseException e) {
      log.error("결제 취소 실패, IamPort API 오류 발생, 결제번호 = {}", paymentApiId, e);
    } catch (IOException e) {
      log.error("결제 취소 실패, IamPort 서버 통신 중 네트워크 오류 발생", e);
    }
    return iamportResponse != null && iamportResponse.getCode() == 200;
  }

  @Override
  public boolean verify(String paymentApiId, long billingAmount) {
    long payAmount = 0;

    try {
      IamportResponse<Payment> iamportResponse = api.paymentByImpUid(paymentApiId);
      payAmount = iamportResponse.getResponse().getAmount().longValue();
    } catch (IamportResponseException e) {
      log.error("결제 사전검증 실패, IamPort API 오류 발생, 결제번호 = {}", paymentApiId, e);
    } catch (IOException e) {
      log.error("결제 사전검증 실패, IamPort 서버 통신 중 네트워크 오류 발생", e);
    }

    return payAmount == billingAmount;
  }
}
