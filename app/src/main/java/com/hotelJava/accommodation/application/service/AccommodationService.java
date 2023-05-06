package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.application.port.DeleteAccommodationUseCase;
import com.hotelJava.accommodation.application.port.SaveAccommodationUseCase;
import com.hotelJava.accommodation.application.port.UpdateAccommodationUseCase;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequestDto;
import com.hotelJava.accommodation.util.AccommodationMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.picture.domain.Picture;
import com.hotelJava.picture.util.PictureMapper;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.util.RoomMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService implements SaveAccommodationUseCase, UpdateAccommodationUseCase, DeleteAccommodationUseCase {

  private final AccommodationRepository accommodationRepository;

  private final AccommodationMapper accommodationMapper;

  private final RoomMapper roomMapper;

  private final PictureMapper pictureMapper;

  @Transactional
  @Override
  public CreateAccommodationResponseDto saveAccommodation(
      CreateAccommodationRequestDto createAccommodationRequestDto) {
    validateDuplicatedAccommodation(createAccommodationRequestDto);

    Accommodation accommodation = accommodationMapper.toEntity(createAccommodationRequestDto);
    Picture accommodationPicture =
        pictureMapper.toEntity(createAccommodationRequestDto.getPictureDto());

    List<Room> rooms =
        createAccommodationRequestDto.getCreateRoomRequestDtos().stream()
            .map(
                createRoomRequestDto -> {
                  Room room = roomMapper.toEntity(createRoomRequestDto);

                  createRoomRequestDto.getPictureDtos().stream()
                      .map(pictureMapper::toEntity)
                      .forEach(room::addPicture);

                  return room;
                })
            .toList();

    accommodation.createAccommodation(rooms, accommodationPicture);

    return accommodationMapper.toCreateAccommodationResponseDto(
        accommodationRepository.save(accommodation));
  }

  @Transactional
  @Override
  public void updateAccommodation(
      Long accommodationId, UpdateAccommodationRequestDto updateAccommodationRequestDto) {

    Accommodation accommodation =
        accommodationRepository
            .findById(accommodationId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.ACCOMMODATION_NOT_FOUND));

    accommodation.updateAccommodation(
        updateAccommodationRequestDto.getName(),
        updateAccommodationRequestDto.getType(),
        updateAccommodationRequestDto.getPhoneNumber(),
        updateAccommodationRequestDto.getAddress(),
        pictureMapper.toEntity(updateAccommodationRequestDto.getPictureDto()),
        updateAccommodationRequestDto.getDescription());
  }

  @Transactional
  @Override
  public void deleteAccommodation(Long accommodationId) {
    accommodationRepository
        .findById(accommodationId)
        .ifPresentOrElse(
            accommodationRepository::delete,
            () -> {
              throw new BadRequestException(ErrorCode.ACCOMMODATION_NOT_FOUND);
            });
  }
  
  private void validateDuplicatedAccommodation(
      CreateAccommodationRequestDto createAccommodationRequestDto) {
    if (accommodationRepository.existsByName(createAccommodationRequestDto.getName())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_NAME_FOUND);
    }
  }
}
