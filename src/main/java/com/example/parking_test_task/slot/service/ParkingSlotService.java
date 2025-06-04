package com.example.parking_test_task.slot.service;

import com.example.parking_test_task.slot.entity.dto.ParkingSlotRequestDto;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotResponseDto;

public interface ParkingSlotService {
    ParkingSlotResponseDto createParkingSlot(ParkingSlotRequestDto parkingSlotRequestDto);

    void deleteParkingSlot(Long slotId);

    ParkingSlotResponseDto changeAvailable(Long slotId);
}
