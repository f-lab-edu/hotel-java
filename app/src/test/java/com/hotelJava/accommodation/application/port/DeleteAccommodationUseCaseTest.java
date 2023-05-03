package com.hotelJava.accommodation.application.port;

import com.hotelJava.accommodation.domain.Accommodation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DeleteAccommodationUseCaseTest extends BeforeEachInit {

  @Autowired DeleteAccommodationQuery sut;

  @DisplayName("숙소 삭제")
  @Test
  void 숙소_삭제() {
    // given

    // when
    sut.deleteAccommodation(accommodation1.getId());
    Optional<Accommodation> deletedAccommodation =
        accommodationRepository.findById(accommodation1.getId());

    // then
    assertThat(deletedAccommodation).isEmpty();
  }
}
