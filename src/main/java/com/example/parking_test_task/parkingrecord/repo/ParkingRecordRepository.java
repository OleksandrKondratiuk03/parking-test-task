package com.example.parking_test_task.parkingrecord.repo;

import com.example.parking_test_task.parkingrecord.entity.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

    Optional<ParkingRecord> findByLicencePlate(String licencePlate);

    @Query("select r " +
            "from  ParkingRecord r " +
            "where r.parkingSlot.floor.parkingLot.id = :lotId")
    List<ParkingRecord> findAllByParkingLot(@Param("lotId") Long lotId);
}
