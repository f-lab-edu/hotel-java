//package com.hotelJava.reservation.application.port.in;
//
//import static com.hotelJava.reservation.util.ReservationMapper.*;
//import static org.mockito.Mockito.doReturn;
//
//import com.hotelJava.CommandTestFixture;
//import com.hotelJava.DomainTestFixture;
//import com.hotelJava.accommodation.application.port.out.persistence.SaveAccommodationPort;
//import com.hotelJava.accommodation.domain.Accommodation;
//import com.hotelJava.accommodation.domain.Room;
//import com.hotelJava.common.embeddable.CheckDate;
//import com.hotelJava.member.application.port.out.persistence.RegisterMemberPort;
//import com.hotelJava.member.domain.Member;
//import com.hotelJava.reservation.application.port.in.command.ReserveCommand;
//import com.hotelJava.reservation.application.port.in.result.ReserveResult;
//import com.hotelJava.reservation.application.port.out.persistence.FindReservationPort;
//import com.hotelJava.reservation.domain.Reservation;
//import com.hotelJava.reservation.domain.ReserveType;
//import com.hotelJava.reservation.util.RandomReservationNumberGenerator;
//import java.time.LocalDate;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class ReserveUseCaseTest {
//  @Autowired private ReserveUseCase sut;
//  @Autowired private RegisterMemberPort registerMemberPort;
//  @Autowired private SaveAccommodationPort saveAccommodationPort;
//  @Autowired private FindReservationPort findReservationPort;
//  @SpyBean private RandomReservationNumberGenerator randomReservationNumberGenerator;
//
//  @Test
//  @DisplayName("Eager 타입의 예약 요청이 있을 때, 결제 대기중인 예약 정보가 생성되고, 즉시 재고를 감소시킨다.")
//  void 즉시예약_재고감소() {
//    // given
//    Member member = DomainTestFixture.member();
//    registerMemberPort.register(member);
//
//    int maxOccupancy = 5;
//    int quantity = 5;
//    Accommodation accommodation = DomainTestFixture.accommodation();
//    Room room = DomainTestFixture.room(maxOccupancy, quantity, 30000, LocalDate.now(), 5);
//    accommodation.addRoom(room);
//    saveAccommodationPort.save(accommodation);
//
//    doReturn("example-reservationNo")
//        .when(randomReservationNumberGenerator)
//        .generateReservationNumber();
//
//    // when
//    CheckDate checkDate = new CheckDate(LocalDate.now(), 3);
//    ReserveCommand reserveCommand = CommandTestFixture.reserveCommand(ReserveType.EAGER, checkDate, 1);
//
//    ReserveResult reserveResult = sut.reserve(member.getEmail(), room.getId(), reserveCommand);
//
//    // then
//    Reservation reservation = findReservationPort.findByReservationNo("example-reservationNo");
//    Assertions.assertThat(RESERVATION_MAPPER.toReserveResult(reservation)).isEqualTo(reserveResult);
//    Assertions.assertThat(
//            room.getStocks().values().stream()
//                .filter(stock -> checkDate.matches(stock.getDate()))
//                .allMatch(stock -> stock.getQuantity() == quantity - 1))
//        .isTrue();
//  }
//
//  @Test
//  @DisplayName("객실이 품절이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 즉시예약_품절() {}
//
//  @Test
//  @DisplayName("예약객실 최대 인원을 초과하는 예약 요청이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 즉시예약_인원초과() {}
//
//  @Test
//  @DisplayName("예약하려고하는 객실이 없다면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 즉시예약_객실조회실패() {}
//
//  @Test
//  @DisplayName("예약회원이 없다면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 즉시예약_회원조회실패() {}
//
//  @Test
//  @DisplayName("Lazy 타입의 예약 요청이 있을 때, 결제 대기중인 예약 정보가 생성되고, 즉시 재고를 감소시킨다.")
//  void 지연예약_재고감소() {}
//
//  @Test
//  @DisplayName("객실이 품절이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 지연예약_품절() {}
//
//  @Test
//  @DisplayName("예약객실 최대 인원을 초과하는 예약 요청이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 지연예약_인원초과() {}
//
//  @Test
//  @DisplayName("예약하려고하는 객실이 없다면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 지연예약_객실조회실패() {}
//
//  @Test
//  @DisplayName("예약회원이 없다면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 지연예약_회원조회실패() {}
//}
