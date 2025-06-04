package com.example.parking_test_task.lot.service.impl;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.lot.entity.dto.ParkingLotRequestDto;
import com.example.parking_test_task.lot.entity.dto.ParkingLotResponseDto;
import com.example.parking_test_task.lot.repo.ParkingLotRepository;
import com.example.parking_test_task.lot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    @Override
    public ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotRequestDto) {

        if(parkingLotRequestDto.getName() == null){
            log.error("All fields are required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ALL_FIELDS_REQUIRED");
        }

        ParkingLot parkingLot = ParkingLot.builder()
                .name(parkingLotRequestDto.getName())
                .floors(new ArrayList<>())
                .build();

        parkingLot = parkingLotRepository.save(parkingLot);

        log.info("Parking lot saved successfully");

        return ParkingLotResponseDto.builder()
                .id(parkingLot.getId())
                .name(parkingLot.getName())
                .floors(parkingLot.getFloors().stream().map(Floor::getId).collect(Collectors.toList()))
                .build();
    }

    @Override
    public void deleteParkingLot(Long lotId) {
        parkingLotRepository.deleteById(lotId);
        log.info("Parking lot deleted successfully");
    }
}
