package com.example.parking_test_task.parkingrecord.service;

import com.example.parking_test_task.parkingrecord.entity.dto.ParkingRecordResponseDto;

import java.util.List;

public interface ParkingRecordService {
    List<ParkingRecordResponseDto> getAllRecords(Long lotId);
}
