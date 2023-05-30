package com.hotelJava.accommodation.adapter.in.web;

import com.hotelJava.accommodation.application.port.in.DeleteAccommodationUseCase;
import com.hotelJava.accommodation.application.port.in.RegisterAccommodationUseCase;
import com.hotelJava.accommodation.application.port.in.SearchAccommodationQuery;
import com.hotelJava.accommodation.application.port.in.UpdateAccommodationUseCase;
import com.hotelJava.accommodation.application.port.in.command.RegisterAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.command.UpdateAccommodationCommand;
import com.hotelJava.accommodation.application.port.in.result.RegisterAccommodationResult;
import com.hotelJava.accommodation.application.port.in.result.SearchAccommodationResult;
import com.hotelJava.accommodation.domain.AccommodationType;
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

  private final RegisterAccommodationUseCase registerAccommodationUseCase;

  private final SearchAccommodationQuery searchAccommodationQuery;

  private final UpdateAccommodationUseCase updateAccommodationUseCase;

  private final DeleteAccommodationUseCase deleteAccommodationUseCase;

  @GetMapping("/{type}/{firstLocation}/{secondLocation}")
  public List<SearchAccommodationResult> findAccommodations(
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
    return searchAccommodationQuery.search(
        type, firstLocation, secondLocation, name, checkInDate, checkOutDate, guestCount);
  }

  @PostMapping
  public RegisterAccommodationResult register(
      @Valid @RequestBody RegisterAccommodationCommand registerAccommodationCommand) {
    return registerAccommodationUseCase.register(registerAccommodationCommand);
  }

  @PutMapping("/{encodedAccommodationId}")
  public HttpStatus update(
      @PathVariable("encodedAccommodationId") DecodeId accommodationId,
      @Valid @RequestBody UpdateAccommodationCommand updateAccommodationCommand) {
    updateAccommodationUseCase.updateAccommodation(
        accommodationId.getDecodeId(), updateAccommodationCommand);

    return HttpStatus.OK;
  }

  @DeleteMapping("/{encodedAccommodationId}")
  public HttpStatus delete(@PathVariable("encodedAccommodationId") DecodeId accommodationId) {
    deleteAccommodationUseCase.deleteAccommodation(accommodationId.getDecodeId());

    return HttpStatus.OK;
  }
}
