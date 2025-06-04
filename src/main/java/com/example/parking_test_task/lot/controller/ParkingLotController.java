package com.example.parking_test_task.lot.controller;

import com.example.parking_test_task.lot.entity.dto.ParkingLotRequestDto;
import com.example.parking_test_task.lot.entity.dto.ParkingLotResponseDto;
import com.example.parking_test_task.lot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    public ResponseEntity<ParkingLotResponseDto> createParkingLot(@RequestBody ParkingLotRequestDto parkingLotRequestDto){
        return ResponseEntity.ok(parkingLotService.createParkingLot(parkingLotRequestDto));
    }

    @DeleteMapping("/{lotId}")
    public ResponseEntity<Void> deleteLot(@PathVariable Long lotId){
        parkingLotService.deleteParkingLot(lotId);
        return ResponseEntity.ok().build();
    }
}
