package com.hotelJava.common.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheckTime {

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkInTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalDateTime checkOutTime;
}
