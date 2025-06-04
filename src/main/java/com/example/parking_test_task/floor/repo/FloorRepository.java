package com.example.parking_test_task.floor.repo;

import com.example.parking_test_task.floor.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    @Query("select max(f.floorNumber) " +
            "from Floor f " +
            "where f.parkingLot.id = :lotId")
    Integer getLastFloorNumber(@Param("lotId") Long parkingLotId);
}
