package com.hotelJava.picture.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PictureInfo {

    private String name;

    private String originFileName;

    private String saveFileName;

    private String extension;

    private String fullPath;

    private long fileSize;
}
