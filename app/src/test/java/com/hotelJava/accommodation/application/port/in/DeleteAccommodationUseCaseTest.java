package com.hotelJava.accommodation.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.hotelJava.DomainTestFixture;
import com.hotelJava.accommodation.application.port.out.persistence.FindAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DeleteAccommodationUseCaseTest {

  @Autowired private DeleteAccommodationUseCase sut;
  @SpyBean private FindAccommodationPort findAccommodationPort;

  @DisplayName("숙소 삭제를 하면, true를 반환하고, deleted 필드가 true로 변경된다.")
  @Test
  void 숙소삭제() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();

    // when
    doReturn(accommodation).when(findAccommodationPort).findById(anyLong());

    // then
    assertThat(sut.deleteAccommodation(1L)).isTrue();
    assertThat(accommodation.isDeleted()).isTrue();
  }

  @DisplayName("이미 삭제된 숙소를 삭제하면, false를 반환한다.")
  @Test
  void 숙소삭제_이미삭제된숙소() {
    // given
    Accommodation accommodation = DomainTestFixture.accommodation();
    accommodation.delete();

    // when
    doReturn(accommodation).when(findAccommodationPort).findById(anyLong());

    // then
    assertThat(sut.deleteAccommodation(1L)).isFalse();
  }
}
