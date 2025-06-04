package com.example.parking_test_task.slot.repo;

import com.example.parking_test_task.slot.entity.ParkingSlot;
import com.example.parking_test_task.slot.entity.enums.Type;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p " +
            "from ParkingSlot p " +
            "where p.available = true " +
            "and p.type = :vehicleType")
    ParkingSlot findAvailableSlots(@Param("vehicleType") Type type);

    @Query("select max(p.slotNumber) " +
            "from ParkingSlot p " +
            "where p.floor.id= :floorId")
    Integer getLastSlotNumber(@Param("floorId") Long floorId);
}
