package com.hotelJava.accommodation.controller;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Address;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import com.hotelJava.accommodation.service.AccommodationService;
import com.hotelJava.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
public class AccommodationController {

  private final AccommodationService accommodationService;

  @GetMapping
  public String test() {
    return "test";
  }

  @GetMapping("/{type}/{firstLocation}/{secondLocation}")
  public ApiResponse<List<AccommodationResponseDto>> findAccommodations(
      @PathVariable("type") String type,
      @PathVariable("firstLocation") String firstLocation,
      @PathVariable("secondLocation") String secondLocation,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) LocalDateTime checkInDate,
      @RequestParam(required = false) LocalDateTime checkOutDate,
      @RequestParam(required = false) String guestCount,
      @RequestParam(required = false, defaultValue = "1") int curPage,
      @RequestParam(required = false, defaultValue = "10") int limit) {

    // TODO: 조회 파라미터 여부에 따른 분기 처리, 페이징 처리 해야함
//    List<Accommodation> accommodations = accommodationService.findByTypeAndLocation(type, firstLocation, secondLocation, accommodationRequestDto, curPage, limit);

    Address address = Address.builder()
            .firstLocation(firstLocation)
            .secoundLocation(secondLocation)
            .build();

    List<Accommodation> accommodations = accommodationService.findByTypeAndAddressFirstLocation(AccommodationType.valueOf(type), address);

    return ApiResponse.success(accommodations.stream().map(AccommodationResponseDto::of).toList());
  }

//  @PostMapping()
//  public ApiResponse<AccommodationResponseDto> create(
//      @Valid @RequestBody final AccommodationRequestDto accommodationRequestDto) {
//
//    // 비즈니스 로직 처리
//
//    // AccommodationResponseDto에 담기
//    AccommodationResponseDto accommodationResponseDto =
//        AccommodationResponseDto.builder()
//            .name("가평 V10 풀빌라")
//            .address("경기 가평군 청평면 고성리 278-4")
//            .location("가평/양평/포천")
//            .type(AccommodationType.PENSION.getValue())
//            .phoneNumber("1677-0000")
//            .description("가평 풀빌라입니다.")
//            .build();
//
//    return ApiResponse.success(accommodationResponseDto);
//  }
}
