package com.hotelJava.accommodation.controller;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.CreateAccommodationRequestDto;
import com.hotelJava.accommodation.dto.CreateAccommodationResponseDto;
import com.hotelJava.accommodation.dto.FindAccommodationResponseDto;
import com.hotelJava.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
public class AccommodationController {

  private final AccommodationService accommodationService;

  @GetMapping("/{type}/{firstLocation}/{secondLocation}")
  public List<FindAccommodationResponseDto> findAccommodations(
      @PathVariable(name = "type", required = true) AccommodationType type,
      @PathVariable(name = "firstLocation", required = true) String firstLocation,
      @PathVariable(name = "secondLocation", required = true) String secondLocation,
      @RequestParam(required = false, defaultValue = "") String name,
      @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
          LocalDate checkInDate,
      @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}")
          LocalDate checkOutDate,
      @RequestParam(required = false, defaultValue = "2") int guestCount) {
    return accommodationService.findAccommodations(
        type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);
  }

  @PostMapping
  public CreateAccommodationResponseDto createAccommodation(
      @Valid @RequestBody CreateAccommodationRequestDto createAccommodationRequestDto) {
        return accommodationService.saveAccommodation(createAccommodationRequestDto);
  }
}
