package com.hotelJava.inventory.repository;

import com.hotelJava.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  List<Inventory> findByRoomId(Long roomId);
}
