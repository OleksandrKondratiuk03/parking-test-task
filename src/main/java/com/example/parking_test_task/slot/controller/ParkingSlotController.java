package com.example.parking_test_task.slot.controller;

import com.example.parking_test_task.slot.entity.dto.ParkingSlotRequestDto;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotResponseDto;
import com.example.parking_test_task.slot.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @PostMapping()
    public ResponseEntity<ParkingSlotResponseDto> createParkingSlot(@RequestBody ParkingSlotRequestDto parkingSlotRequestDto){
        return ResponseEntity.ok(parkingSlotService.createParkingSlot(parkingSlotRequestDto));
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteParkingSlot(@PathVariable Long slotId){
        parkingSlotService.deleteParkingSlot(slotId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{slotId}")
    public ResponseEntity<ParkingSlotResponseDto> changeAvailability(@PathVariable Long slotId){
        return ResponseEntity.ok(parkingSlotService.changeAvailable(slotId));
    }

}
