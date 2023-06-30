package com.hotelJava.accommodation.application.service;

import com.hotelJava.accommodation.adapter.persistence.AccommodationRepository;
import com.hotelJava.accommodation.application.port.DeleteAccommodationUseCase;
import com.hotelJava.accommodation.application.port.CreateAccommodationUseCase;
import com.hotelJava.accommodation.application.port.UpdateAccommodationUseCase;
import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.dto.CreateAccommodationRequest;
import com.hotelJava.accommodation.dto.CreateAccommodationResponse;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequest;
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
public class AccommodationService implements CreateAccommodationUseCase, UpdateAccommodationUseCase, DeleteAccommodationUseCase {

  private final AccommodationRepository accommodationRepository;

  private final AccommodationMapper accommodationMapper;

  private final RoomMapper roomMapper;

  private final PictureMapper pictureMapper;

  @Transactional
  @Override
  public CreateAccommodationResponse createAccommodation(
      CreateAccommodationRequest createAccommodationRequest) {
    validateDuplicatedAccommodation(createAccommodationRequest);

    Accommodation accommodation = accommodationMapper.toEntity(createAccommodationRequest);
    Picture accommodationPicture =
        pictureMapper.toEntity(createAccommodationRequest.getPictureDto());

    List<Room> rooms =
        createAccommodationRequest.getCreateRoomRequests().stream()
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

    return accommodationMapper.toCreateAccommodationResponse(
        accommodationRepository.save(accommodation));
  }

  @Transactional
  @Override
  public void updateAccommodation(
      Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {

    Accommodation accommodation =
        accommodationRepository
            .findById(accommodationId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.ACCOMMODATION_NOT_FOUND));

    accommodation.updateAccommodation(
        updateAccommodationRequest.getName(),
        updateAccommodationRequest.getType(),
        updateAccommodationRequest.getPhoneNumber(),
        updateAccommodationRequest.getAddress(),
        pictureMapper.toEntity(updateAccommodationRequest.getPictureDto()),
        updateAccommodationRequest.getDescription());
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
      CreateAccommodationRequest createAccommodationRequest) {
    if (accommodationRepository.existsByName(createAccommodationRequest.getName())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_NAME_FOUND);
    }
  }
}
