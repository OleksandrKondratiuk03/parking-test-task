package com.example.parking_test_task.parkingrecord.service.impl;

import com.example.parking_test_task.parkingrecord.entity.ParkingRecord;
import com.example.parking_test_task.parkingrecord.entity.dto.ParkingRecordResponseDto;
import com.example.parking_test_task.parkingrecord.repo.ParkingRecordRepository;
import com.example.parking_test_task.parkingrecord.service.ParkingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingRecordServiceImpl implements ParkingRecordService {

    private final ParkingRecordRepository parkingRecordRepository;

    @Override
    public List<ParkingRecordResponseDto> getAllRecords(Long lotId) {
        List<ParkingRecord> parkingRecords = parkingRecordRepository.findAllByParkingLot(lotId);

        log.info("Parking records retrieved");

        return parkingRecords.stream().map(parkingRecord ->
                new ParkingRecordResponseDto(parkingRecord.getLicencePlate(),
                        parkingRecord.getParkingSlot().getFloor().getFloorNumber(),
                        parkingRecord.getParkingSlot().getSlotNumber(), parkingRecord.getEntryTime(),
                        parkingRecord.getExitTime())).toList();
    }
}
