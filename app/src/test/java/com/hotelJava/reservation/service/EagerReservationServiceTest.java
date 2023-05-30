//package com.hotelJava.reservation.service;
//
//import com.github.javafaker.Faker;import com.hotelJava.TestFixture;
//import com.hotelJava.accommodation.domain.Accommodation;import com.hotelJava.common.embeddable.CheckDate;import com.hotelJava.member.domain.Member;import com.hotelJava.member.repository.MemberRepository;import com.hotelJava.reservation.domain.Reservation;import com.hotelJava.reservation.domain.ReservationCommand;
//import com.hotelJava.reservation.domain.ReservationInfo;import com.hotelJava.reservation.dto.CreateReservationRequestDto;import com.hotelJava.reservation.dto.CreateReservationResponseDto;import com.hotelJava.reservation.repository.ReservationRepository;import com.hotelJava.room.domain.Room;import com.hotelJava.room.repository.RoomRepository;import org.assertj.core.api.Assertions;import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;import org.mockito.Mock;import org.mockito.Mockito;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.mock.mockito.MockBean;
//import java.time.LocalDate;import java.util.NoSuchElementException;import java.util.Optional;import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class EagerReservationServiceTest {
//++
//  private static final Faker faker = Faker.instance();
//
//  @MockBean private MemberRepository memberRepository;
//  @MockBean private ReservationRepository reservationRepository;
//  @MockBean private RoomRepository roomRepository;
//  @Autowired private ReservationService reservationService;
//
//  @Test
//  @DisplayName("정상 예약 요청이 있을 때, 결제 대기중인 예약 정보를 반환하고, 즉시 재고를 감소시킨다.")
//  void 예약() {
//    // given
//    int duration = faker.number().numberBetween(1, 5);
//    int numberOfGuests = faker.number().numberBetween(1, 5);
//    int maxOccupancy = faker.number().numberBetween(1, 5);
//    int quantity = faker.number().numberBetween(1, 5);
//    Room room = TestFixture.getRoom(maxOccupancy, quantity, LocalDate.now(), duration);
//    Member member = TestFixture.getMember();
//    CreateReservationRequestDto request = TestFixture.getReservationRequestDto(ReservationCommand.EAGER, new CheckDate(LocalDate.now(), duration),numberOfGuests);
//
//    doReturn(room).when(roomRepository).findById(anyLong());
//    doReturn(member).when(memberRepository).findByEmail(anyString());
//
//    // when
//    ReservationInfo reservationInfo = reservationService.reserve(faker.number().randomNumber(), faker.internet().emailAddress(), request);
//
//    // then
//    ReservationInfo expect = CreateReservationResponseDto.builder().reservationNo()
//
//    Assertions.assertThat(findReservation).isEqualTo()
//  }
//
//  @Test
//  @DisplayName("객실이 품절이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 예약_품절() {
//    int duration = faker.number().numberBetween(1, 5);
//    int numberOfGuests = faker.number().numberBetween(1, 5);
//    CreateReservationRequestDto request = TestFixture.getReservationRequestDto(ReservationCommand.EAGER, new CheckDate(LocalDate.now(), duration),numberOfGuests);
//
//  }
//
//  @Test
//  @DisplayName("예약객실 최대 인원을 초과하는 예약 요청이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 예약_인원초과() {
//    int duration = faker.number().numberBetween(1, 5);
//    int numberOfGuests = faker.number().numberBetween(1, 5);
//    TestFixture.getReservationRequestDto(ReservationCommand.EAGER, new CheckDate(LocalDate.now(), duration, );
//  }
//
//  @Test
//  @DisplayName("객실이 품절이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 예약_객실조회실패() {
//    int duration = faker.number().numberBetween(1, 5);
//    int numberOfGuests = faker.number().numberBetween(1, 5);
//    CreateReservationRequestDto request = TestFixture.getReservationRequestDto(ReservationCommand.EAGER, new CheckDate(LocalDate.now(), duration),numberOfGuests);
//
//  }
//
//  @Test
//  @DisplayName("객실이 품절이라면, 재고가 감소하지 않고 예외가 발생한다.")
//  void 예약_회원조회실패() {
//    int duration = faker.number().numberBetween(1, 5);
//    int numberOfGuests = faker.number().numberBetween(1, 5);
//    CreateReservationRequestDto request = TestFixture.getReservationRequestDto(ReservationCommand.EAGER, new CheckDate(LocalDate.now(), duration),numberOfGuests);
//
//  }
//
//
//
//  @Test
//  void confirm() {}
//}
