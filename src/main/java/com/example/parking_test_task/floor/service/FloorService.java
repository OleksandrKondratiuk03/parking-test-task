package com.example.parking_test_task.floor.service;

import com.example.parking_test_task.floor.entity.dto.FloorRequestDto;
import com.example.parking_test_task.floor.entity.dto.FloorResponseDto;

public interface FloorService {
    FloorResponseDto createFloor(FloorRequestDto floorRequestDto);

    void deleteFloor(Long floorId);
}
