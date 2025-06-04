package com.example.parking_test_task.slot.service.impl;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.floor.repo.FloorRepository;
import com.example.parking_test_task.slot.entity.ParkingSlot;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotRequestDto;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotResponseDto;
import com.example.parking_test_task.slot.repo.ParkingSlotRepository;
import com.example.parking_test_task.slot.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final FloorRepository floorRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ParkingSlotResponseDto createParkingSlot(ParkingSlotRequestDto parkingSlotRequestDto) {

        if(parkingSlotRequestDto.getFloorId() == null || parkingSlotRequestDto.getType() == null){
            log.error("All fields are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        Integer lastSlotNumber = parkingSlotRepository.getLastSlotNumber(parkingSlotRequestDto.getFloorId());

        if (lastSlotNumber == null){
            lastSlotNumber=0;
        }

        Floor floor = floorRepository.findById(parkingSlotRequestDto.getFloorId()).orElseThrow(()->{
            log.error("No floor found with id " + parkingSlotRequestDto.getFloorId());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "FLOOR_NOT_FOUND");
        });

        ParkingSlot parkingSlot = ParkingSlot.builder()
                .slotNumber(lastSlotNumber+1)
                .available(true)
                .floor(floor)
                .type(parkingSlotRequestDto.getType())
                .build();

        parkingSlot = parkingSlotRepository.save(parkingSlot);

        log.info("Parking slot saved successfully");

        return ParkingSlotResponseDto.builder()
                .id(parkingSlot.getId())
                .floorId(parkingSlot.getFloor().getId())
                .type(parkingSlot.getType())
                .available(parkingSlot.isAvailable())
                .slotNumber(parkingSlot.getSlotNumber())
                .build();
    }

    @Override
    public void deleteParkingSlot(Long slotId) {
        parkingSlotRepository.deleteById(slotId);
        log.info("Parking slot deleted successfully");
    }

    @Override
    public ParkingSlotResponseDto changeAvailable(Long slotId) {
        ParkingSlot parkingSlot = parkingSlotRepository.findById(slotId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PARKING_SLOT_NOT_FOUND"));
        parkingSlot.setAvailable(!parkingSlot.isAvailable());
        parkingSlotRepository.save(parkingSlot);

        log.info("Parking slot availability changed successfully");

        return ParkingSlotResponseDto.builder()
                .slotNumber(parkingSlot.getSlotNumber())
                .available(parkingSlot.isAvailable())
                .floorId(parkingSlot.getFloor().getId())
                .type(parkingSlot.getType())
                .build();
    }
}
