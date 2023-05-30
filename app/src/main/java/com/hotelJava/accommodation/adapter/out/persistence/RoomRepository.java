package com.hotelJava.accommodation.adapter.out.persistence;

import com.hotelJava.accommodation.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

interface RoomRepository extends JpaRepository<Room, Long> {}
