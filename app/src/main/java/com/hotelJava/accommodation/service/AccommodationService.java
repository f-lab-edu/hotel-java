package com.hotelJava.accommodation.service;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import com.hotelJava.accommodation.domain.Address;
import com.hotelJava.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public List<Accommodation> findByTypeAndAddressFirstLocation(AccommodationType type, Address address) {
        return accommodationRepository.findByTypeAndAddressFirstLocation(type, address.getFirstLocation());
    }
}
