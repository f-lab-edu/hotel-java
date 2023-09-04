package com.hotelJava.room.adapter.persistence;

import com.hotelJava.room.domain.Room;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("")
    Optional<Room> findByIdWithOptimisticLock(@Param("id") Long id);
}
