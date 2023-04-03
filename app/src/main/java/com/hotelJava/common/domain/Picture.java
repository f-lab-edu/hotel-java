package com.hotelJava.common.domain;

import com.hotelJava.accommodation.domain.Accommodation;
import com.hotelJava.common.embeddable.PictureInfo;
import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Picture extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PictureInfo pictureInfo;

    @OneToOne(mappedBy = "picture", fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
