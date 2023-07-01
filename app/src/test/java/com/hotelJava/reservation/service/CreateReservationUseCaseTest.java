package com.hotelJava.reservation.service;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import com.hotelJava.TestFixture;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.member.application.port.out.persistence.FindMemberPort;
import com.hotelJava.member.domain.Member;
import com.hotelJava.reservation.application.port.CreateReservationUseCase;
import com.hotelJava.reservation.dto.CreateReservationRequest;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.adapter.persistence.RoomRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CreateReservationUseCaseTest {

  @Autowired
  @Qualifier("kyungtakReservationService")
  private CreateReservationUseCase sut;

  @SpyBean private FindMemberPort findMemberPort;

  @SpyBean private RoomRepository roomRepository;
  
  @Test
  @DisplayName("사용자가 선택한 숙박 기간(체크인, 체크아웃)과 해당하는 객실에 예약한다.")
  void 객실_예약() {
    // given
    Member member = TestFixture.getMember();
    doReturn(member).when(findMemberPort).findByEmail(member.getEmail());

    Room room = TestFixture.getRoom(10, 10, now(), 10);
    doReturn(Optional.of(room)).when(roomRepository).findById(anyLong());

    CheckDate checkDate = new CheckDate(now(), 10);
    CreateReservationRequest createReservationRequest =
        TestFixture.getCreateReservationRequestDto(checkDate);

    // when & then
    assertDoesNotThrow(
        () ->
            sut.createReservation(
                anyLong(), member.getEmail(), createReservationRequest));
  }
}