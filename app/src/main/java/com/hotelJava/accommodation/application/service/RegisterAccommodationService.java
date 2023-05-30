package com.hotelJava.accommodation.application.service;

import static com.hotelJava.accommodation.util.AccommodationMapper.ACCOMMODATION_MAPPER;

import com.hotelJava.accommodation.application.port.in.RegisterAccommodationUseCase;
import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.result.RegisterAccommodationResult;
import com.hotelJava.accommodation.application.port.out.persistence.CheckDuplicateAccommodationPort;
import com.hotelJava.accommodation.application.port.out.persistence.SaveAccommodationPort;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RegisterAccommodationService implements RegisterAccommodationUseCase {

  private final SaveAccommodationPort saveAccommodationPort;
  private final CheckDuplicateAccommodationPort checkDuplicateAccommodationPort;

  @Override
  public RegisterAccommodationResult register(
      RegisterAccommodationCommand registerAccommodationCommand) {
    validateDuplicatedAccommodation(registerAccommodationCommand.getName());

    Accommodation accommodation = ACCOMMODATION_MAPPER.toEntity(registerAccommodationCommand);

    return ACCOMMODATION_MAPPER.toRegisteredResult(saveAccommodationPort.save(accommodation));
  }

  private void validateDuplicatedAccommodation(String accommodationName) {
    if (checkDuplicateAccommodationPort.isDuplicateByName(accommodationName)) {
      throw new BadRequestException(ErrorCode.DUPLICATED_NAME_FOUND);
    }
  }
}
