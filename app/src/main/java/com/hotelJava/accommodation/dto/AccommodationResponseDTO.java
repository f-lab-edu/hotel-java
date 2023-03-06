package com.hotelJava.accommodation.dto;

import com.hotelJava.accommodation.domain.AccommodationType;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AccommodationResponseDTO {

    private String name;

    private String address;

    private AccommodationType type;

    private int shortPrice;

    private int longPrice;

    private double rating;

    private String phoneNumber;
}
