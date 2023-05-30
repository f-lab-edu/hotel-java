package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.hotelJava.CommandTestFixture;
import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.out.persistence.CheckDuplicateAccommodationPort;
import com.hotelJava.common.error.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class RegisterAccommodationUseCaseTest {

  @Autowired private RegisterAccommodationUseCase sut;
  @SpyBean private CheckDuplicateAccommodationPort checkDuplicateAccommodationPort;

  @Test
  @DisplayName("주어진 이름의 숙소가 등록된 적이 없을 때, 숙소를 정상 등록할 수 있다.")
  void 숙소등록() {
    // given
    RegisterAccommodationCommand command = CommandTestFixture.registerAccommodationCommand();
    doReturn(false).when(checkDuplicateAccommodationPort).isDuplicateByName(anyString());

    // when, then
    sut.register(command);
  }

  @Test
  @DisplayName("숙소 이름이 중복됐을 때, 숙소 등록시 BadRequestException 예외가 발생한다.")
  void 숙소등록_이름중복() {
    // given
    RegisterAccommodationCommand command = CommandTestFixture.registerAccommodationCommand();
    doReturn(true).when(checkDuplicateAccommodationPort).isDuplicateByName(anyString());

    // when, then
    assertThatThrownBy(() -> sut.register(command)).isInstanceOf(BadRequestException.class);
  }
}
