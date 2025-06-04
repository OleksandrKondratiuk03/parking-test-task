package com.example.parking_test_task.lot.service;

import com.example.parking_test_task.lot.entity.dto.ParkingLotRequestDto;
import com.example.parking_test_task.lot.entity.dto.ParkingLotResponseDto;

public interface ParkingLotService {
    ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotRequestDto);

    void deleteParkingLot(Long lotId);
}
