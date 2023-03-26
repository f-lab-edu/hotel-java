package com.hotelJava.accommodation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomPicture {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String originFileName;

    private String saveFileName;

    private String extension;

    private long fileSize;

    private String fullPath;
}
