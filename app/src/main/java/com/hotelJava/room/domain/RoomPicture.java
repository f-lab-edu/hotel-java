package com.hotelJava.room.domain;

import com.hotelJava.common.embeddable.PictureInfo;
import com.hotelJava.common.util.BaseTimeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomPicture extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private PictureInfo pictureInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
