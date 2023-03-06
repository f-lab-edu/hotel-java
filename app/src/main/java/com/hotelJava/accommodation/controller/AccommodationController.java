package com.hotelJava.accommodation.controller;

import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.dto.AccommodationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/accommodation")
public class AccommodationController {

    @GetMapping("/{accommodationType}/{city}")
    public List<AccommodationResponseDTO> findByAccommodationTypeAndCity (
            /*@PathVariable String accommodationType,
            @PathVariable String city,
            @RequestBody AccommodationRequestDTO accommodationRequestDTO*/) {
//        log.info("accommodationType = {}", accommodationType);
//        log.info("city = {}", city);
//        log.info("accommodationRequestDTO = {}", accommodationRequestDTO);

//        return ApiResponse.createSuccess();

        List<AccommodationResponseDTO> accommodationResponseDTOList = new ArrayList<>();

        AccommodationResponseDTO accommodationResponseDTO = AccommodationResponseDTO.builder()
                .name("서울 신라 호텔")
                .address("서울 중구 장충동2가 202")
                .type(AccommodationType.HOTEL_RESORT)
                .shortPrice(100000)
                .longPrice(300000)
                .rating(4.7)
                .phoneNumber("1677-0000")
                .build();
        AccommodationResponseDTO accommodationResponseDTO2 = AccommodationResponseDTO.builder()
                .name("가평 V10 풀빌라")
                .address("경기 가평군 청평면 고성리 278-4")
                .type(AccommodationType.PENSION)
                .shortPrice(100000)
                .longPrice(300000)
                .rating(4.7)
                .phoneNumber("1677-0000")
                .build();

        accommodationResponseDTOList.add(accommodationResponseDTO);
        accommodationResponseDTOList.add(accommodationResponseDTO2);

        return accommodationResponseDTOList;
    }
}
