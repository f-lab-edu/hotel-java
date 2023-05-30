package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.hotelJava.CommandTestFixture;
import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.error.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UpdateAccommodationUseCaseTest {

  @Autowired UpdateAccommodationUseCase sut;
  @SpyBean FindAccommodationPort findAccommodationPort;

  @DisplayName("숙소 정보 수정")
  @Test
  void 숙소수정() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();
    doReturn(accommodation).when(findAccommodationPort).findById(anyLong());

    // when
    UpdateAccommodationCommand command = CommandTestFixture.updateAccommodationCommand();
    sut.updateAccommodation(1L, command);

    // then
    assertThat(command).usingRecursiveComparison().isEqualTo(accommodation);
  }

  @DisplayName("존재하지 않는 숙소라면, 숙소 정보 수정시 예외가 발생한다")
  @Test
  void 숙소수정_숙소조회실패() {
    // given
    doThrow(BadRequestException.class).when(findAccommodationPort).findById(anyLong());

    // when
    UpdateAccommodationCommand command = CommandTestFixture.updateAccommodationCommand();

    // then
    assertThatThrownBy(() -> sut.updateAccommodation(1L, command))
        .isInstanceOf(BadRequestException.class);
  }
}
