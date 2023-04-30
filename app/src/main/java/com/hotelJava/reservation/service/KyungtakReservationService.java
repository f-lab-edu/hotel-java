package com.hotelJava.reservation.service;

import com.hotelJava.common.validation.Validation;
import com.hotelJava.inventory.Inventory;
import com.hotelJava.inventory.repository.InventoryRepository;
import com.hotelJava.payment.service.PaymentService;
import com.hotelJava.reservation.domain.Reservation;
import com.hotelJava.reservation.domain.ReservationCommand;
import com.hotelJava.reservation.dto.CreateReservationRequestDto;
import com.hotelJava.reservation.util.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
public class KyungtakReservationService implements ReservationService {

  private final ReservationMapper reservationMapper;

  private final InventoryRepository inventoryRepository;

  private final PaymentService paymentService;

  private final Validation validation;

  @Override
  public boolean supports(ReservationCommand reservationCommand) {
    return reservationCommand.equals(ReservationCommand.KYUNGTAK_RESERVATION);
  }

  @Transactional
  @Override
  public void saveReservation(
      String encodedAccommodationId,
      String encodedRoomId,
      CreateReservationRequestDto createReservationRequestDto) {

    Long accommodationId = validation.validateIdForEmptyOrNullAndDecoding(encodedAccommodationId);
    Long roomId = validation.validateIdForEmptyOrNullAndDecoding(encodedRoomId);

    // 재고 확인
    List<Inventory> inventories =
        inventoryRepository.findByAccommodationIdAndRoomId(accommodationId, roomId);

    // 예약
    Reservation reservation = reservationMapper.toEntity(createReservationRequestDto);

    // 결제
    paymentService.savePayment();
  }
}
