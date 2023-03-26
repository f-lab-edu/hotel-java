package com.hotelJava.accommodation.repository;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.accommodation.domain.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findByTypeAndAddressFirstLocation(AccommodationType type, String firstLocation);
}
