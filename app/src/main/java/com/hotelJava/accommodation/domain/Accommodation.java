package com.hotelJava.accommodation.domain;

import com.hotelJava.common.uril.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accommodation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    private double rating;

    private String phoneNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AccommodationPicture_id")
    private AccommodationPicture accommodationPicture;

    private String description;
}
