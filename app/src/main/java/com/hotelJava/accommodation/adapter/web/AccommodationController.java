package com.hotelJava.accommodation.adapter.web;

import com.hotelJava.accommodation.application.port.DeleteAccommodationUseCase;
import com.hotelJava.accommodation.application.port.FindAccommodationQuery;
import com.hotelJava.accommodation.application.port.CreateAccommodationUseCase;
import com.hotelJava.accommodation.application.port.UpdateAccommodationUseCase;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.CreateAccommodationRequest;
import com.hotelJava.accommodation.dto.CreateAccommodationResponse;
import com.hotelJava.accommodation.dto.FindAccommodationResponse;
import com.hotelJava.accommodation.dto.UpdateAccommodationRequest;
import com.hotelJava.common.dto.DecodeId;
import com.hotelJava.member.domain.Role;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
public class AccommodationController {

  private final CreateAccommodationUseCase createAccommodationUseCase;

  private final FindAccommodationQuery findAccommodationQuery;

  private final UpdateAccommodationUseCase updateAccommodationUseCase;

  private final DeleteAccommodationUseCase deleteAccommodationUseCase;

  @GetMapping("/{type}/{firstLocation}/{secondLocation}")
  public List<FindAccommodationResponse> findAccommodations(
      @PathVariable(name = "type", required = true) AccommodationType type,
      @PathVariable(name = "firstLocation", required = true) String firstLocation,
      @PathVariable(name = "secondLocation", required = true) String secondLocation,
      @RequestParam(required = false, defaultValue = "") String name,
      @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
          LocalDate checkInDate,
      @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().plusDays(1)}")
          LocalDate checkOutDate,
      @RequestParam(required = false, defaultValue = "2") int guestCount,
      @AuthenticationPrincipal(expression = "roles") List<Role> roles) {
    return findAccommodationQuery.findAccommodations(
        type,
        firstLocation,
        secondLocation,
        name,
        checkInDate,
        checkOutDate,
        guestCount,
        roles.get(0));
  }

  @PostMapping
  public CreateAccommodationResponse createAccommodation(
      @Valid @RequestBody CreateAccommodationRequest createAccommodationRequest) {
    return createAccommodationUseCase.createAccommodation(createAccommodationRequest);
  }

  @PutMapping("/{encodedAccommodationId}")
  public HttpStatus updateAccommodation(
      @PathVariable("encodedAccommodationId") DecodeId accommodationId,
      @Valid @RequestBody UpdateAccommodationRequest updateAccommodationRequest) {
    updateAccommodationUseCase.updateAccommodation(
        accommodationId.getDecodeId(), updateAccommodationRequest);

    return HttpStatus.OK;
  }

  @DeleteMapping("/{encodedAccommodationId}")
  public HttpStatus deleteAccommodation(
      @PathVariable("encodedAccommodationId") DecodeId accommodationId) {
    deleteAccommodationUseCase.deleteAccommodation(accommodationId.getDecodeId());

    return HttpStatus.OK;
  }
}
