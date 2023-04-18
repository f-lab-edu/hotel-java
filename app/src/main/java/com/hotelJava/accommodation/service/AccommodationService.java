package com.hotelJava.accommodation.service;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;
import com.hotelJava.accommodation.dto.FindAccommodationResponseDto;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequestDto;
import com.hotelJava.accommodation.picture.domain.Picture;
import com.hotelJava.accommodation.picture.util.PictureMapper;
import com.hotelJava.accommodation.repository.AccommodationRepository;
import com.hotelJava.accommodation.util.AccommodationMapper;
import com.hotelJava.common.error.ErrorCode;
import com.hotelJava.common.error.exception.BadRequestException;
import com.hotelJava.common.error.exception.InternalServerException;
import com.hotelJava.common.util.Base32Util;
import com.hotelJava.room.domain.Room;
import com.hotelJava.room.util.RoomMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {

  private final AccommodationRepository accommodationRepository;

  private final AccommodationMapper accommodationMapper;

  private final RoomMapper roomMapper;

  private final PictureMapper pictureMapper;

  private final Base32Util base32Util;

  public List<FindAccommodationResponseDto> findAccommodations(
      AccommodationType type,
      String firstLocation,
      String secondLocation,
      String name,
      LocalDate checkInDate,
      LocalDate checkOutDate,
      int guestCount) {
    List<Accommodation> accommodations =
        accommodationRepository.findAccommodations(
            type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);
    return accommodations.stream()
        .map(
            accommodation -> {
              int minimumRoomPrice =
                  accommodation.getRooms().stream()
                      .mapToInt(Room::getPrice)
                      .min()
                      .orElseThrow(
                          () -> new InternalServerException(ErrorCode.NO_MINIMUM_PRICE_FOUND));
              return accommodationMapper.toFindAccommodationResponseDto(
                  minimumRoomPrice, accommodation);
            })
        .collect(Collectors.toList());
  }

  @Transactional
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

  private void validateDuplicatedAccommodation(
      CreateAccommodationRequestDto createAccommodationRequestDto) {
    if (accommodationRepository.existsByName(createAccommodationRequestDto.getName())) {
      throw new BadRequestException(ErrorCode.DUPLICATED_NAME_FOUND);
    }
  }

  @Transactional
  public void updateAccommodation(
      String encodedAccommodationId, UpdateAccommodationRequestDto updateAccommodationRequestDto) {
    String accommodationId = base32Util.decode(encodedAccommodationId);

    Accommodation accommodation =
        accommodationRepository
            .findById(Long.parseLong(accommodationId))
            .orElseThrow(() -> new BadRequestException(ErrorCode.ACCOMMODATION_NOT_FOUND));

    accommodation.updateAccommodation(
        updateAccommodationRequestDto.getName(),
        updateAccommodationRequestDto.getType(),
        updateAccommodationRequestDto.getPhoneNumber(),
        updateAccommodationRequestDto.getAddress(),
        pictureMapper.toEntity(updateAccommodationRequestDto.getPictureDto()),
        updateAccommodationRequestDto.getDescription());
  }
}
