package com.hotelJava.accommodation.controller;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.AccommodationCreateDto;
import com.hotelJava.accommodation.dto.AccommodationResponseDto;
import com.hotelJava.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @GetMapping("/{type}/{location}")
    public List<AccommodationResponseDto> findByAccommodationTypeAndCity(
            @PathVariable(required = false) String type,
            @PathVariable(required = false) String location,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String checkInDate,
            @RequestParam(required = false) String checkOutDate,
            @RequestParam(required = false) String guests,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        List<AccommodationResponseDto> accommodationResponseDtoList = new ArrayList<>();

        AccommodationResponseDto accommodationResponseDto = AccommodationResponseDto.builder()
                .name("서울 신라 호텔")
                .address("서울 중구 장충동2가 202")
                .type(AccommodationType.HOTEL_RESORT)
                .shortPrice(100000)
                .longPrice(300000)
                .rating(4.7)
                .phoneNumber("1677-0000")
                .build();
        AccommodationResponseDto accommodationResponseDto2 = AccommodationResponseDto.builder()
                .name("가평 V10 풀빌라")
                .address("경기 가평군 청평면 고성리 278-4")
                .type(AccommodationType.PENSION)
                .shortPrice(100000)
                .longPrice(300000)
                .rating(4.7)
                .phoneNumber("1677-0000")
                .build();

        accommodationResponseDtoList.add(accommodationResponseDto);
        accommodationResponseDtoList.add(accommodationResponseDto2);

        return accommodationResponseDtoList;
    }

    @PostMapping()
    public ApiResponse<AccommodationResponseDto> create(
            /*@Valid*/ @RequestBody AccommodationCreateDto accommodationCreateDto) {

        // 비즈니스 로직 처리

        // AccommodationResponseDto에 담기
        AccommodationResponseDto accommodationResponseDto = AccommodationResponseDto.builder()
                .name("가평 V10 풀빌라")
                .address("경기 가평군 청평면 고성리 278-4")
                .location("가평/양평/포천")
                .type(AccommodationType.PENSION)
                .phoneNumber("1677-0000")
                .description("가평 풀빌라입니다.")
                .build();

        return ApiResponse.success(accommodationResponseDto);
    }
}
