package com.example.parking_test_task.lot.repo;

import com.example.parking_test_task.lot.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
