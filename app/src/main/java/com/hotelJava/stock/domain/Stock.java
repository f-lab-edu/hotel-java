package com.hotelJava.stock.domain;

import com.hotelJava.common.util.BaseTimeEntity;
import com.hotelJava.room.domain.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate date;

    private long quantity;

    public Stock(LocalDate date, long quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    public boolean isZeroQuantity() {
        return quantity <= 0;
    }

    public void calcQuantity(int value) {
        quantity += value;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
