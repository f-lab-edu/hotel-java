package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hotelJava.CommandTestFixture;
import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.application.port.in.command.AddRoomCommand;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.error.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class AddRoomUseCaseTest {

  @Autowired private AddRoomUseCase sut;
  @SpyBean private FindAccommodationPort findAccommodationPort;

  @Test
  @DisplayName("객실을 등록하고자 하는 숙소가 존재하면, 객실을 등록할 수 있다")
  void 객실등록() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();
    AddRoomCommand addRoomCommand = CommandTestFixture.addRoomCommand();
    doReturn(accommodation).when(findAccommodationPort).findById(anyLong());

    // when
    sut.add(1L, addRoomCommand);

    // then
    assertThat(addRoomCommand)
        .usingRecursiveComparison()
        .isEqualTo(accommodation.getRooms().get(0));
  }

  @Test
  @DisplayName("객실을 등록하고자 하는 숙소가 존재하지 않는다면, 객실 등록시 예외가 발생한다.")
  void 객실등록_실패() {
    // given
    AddRoomCommand addRoomCommand = CommandTestFixture.addRoomCommand();
    doThrow(BadRequestException.class).when(findAccommodationPort).findById(anyLong());

    // when & then
    assertThatThrownBy(() -> sut.add(1L, addRoomCommand)).isInstanceOf(BadRequestException.class);
  }
}
