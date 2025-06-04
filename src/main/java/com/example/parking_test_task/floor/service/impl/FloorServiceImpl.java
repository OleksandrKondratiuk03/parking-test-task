package com.example.parking_test_task.floor.service.impl;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.floor.entity.dto.FloorRequestDto;
import com.example.parking_test_task.floor.entity.dto.FloorResponseDto;
import com.example.parking_test_task.floor.repo.FloorRepository;
import com.example.parking_test_task.floor.service.FloorService;
import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.lot.repo.ParkingLotRepository;
import com.example.parking_test_task.slot.entity.ParkingSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FloorResponseDto createFloor(FloorRequestDto floorRequestDto) {

        if(floorRequestDto.getParkingLotId() == null){
            log.error("All fields are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        Integer lastFloorNumber = floorRepository.getLastFloorNumber(floorRequestDto.getParkingLotId());
        if(lastFloorNumber == null){
            lastFloorNumber = 0;
        }
        ParkingLot parkingLot = parkingLotRepository.findById(floorRequestDto.getParkingLotId()).orElseThrow(() -> {
            log.error("No parking lot found with id " + floorRequestDto.getParkingLotId());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "PARKING_LOT_NOT_FOUND");
        });

        Floor floor = Floor.builder()
                .floorNumber(lastFloorNumber + 1)
                .parkingLot(parkingLot)
                .slots(new ArrayList<>())
                .build();

        floor = floorRepository.save(floor);
        log.info("Floor saved successfully");

        return FloorResponseDto.builder()
                .id(floor.getId())
                .floorNumber(floor.getFloorNumber())
                .parkingSlots(floor.getSlots().stream().map(ParkingSlot::getId).toList())
                .parkingLotId(floor.getParkingLot().getId())
                .build();
    }

    @Override
    public void deleteFloor(Long floorId) {
        floorRepository.deleteById(floorId);
        log.info("Floor deleted successfully");
    }
}
