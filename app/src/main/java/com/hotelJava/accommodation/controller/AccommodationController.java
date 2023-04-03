package com.hotelJava.accommodation.controller;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.Address;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import com.hotelJava.accommodation.service.AccommodationService;
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

  @GetMapping("/{type}/{firstLocation}/{secondLocation}")
  public List<AccommodationResponseDto> findAccommodations(
      @PathVariable("type") AccommodationType type,
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
            .secondLocation(secondLocation)
            .build();

    List<Accommodation> accommodations = accommodationService.findByTypeAndAddressFirstLocation(AccommodationType.valueOf(type), address);

    return accommodations.stream().map(AccommodationResponseDto::of).toList();
  }
}
