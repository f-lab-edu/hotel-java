package com.hotelJava.accommodation.application.port.in.command;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.common.embeddable.CheckDate;
import com.hotelJava.common.embeddable.Money;
import lombok.Builder;

@Builder
public record SearchAccommodationCommand(
    AccommodationType type,
    String name,
    String firstLocation,
    String secondLocation,
    CheckDate checkDate,
    Money price,
    Integer numberOfGuests) {}
