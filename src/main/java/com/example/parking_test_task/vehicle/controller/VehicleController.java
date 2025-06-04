package com.example.parking_test_task.vehicle.controller;

import com.example.parking_test_task.vehicle.entity.dto.CheckInDto;
import com.example.parking_test_task.vehicle.entity.dto.CheckOutDto;
import com.example.parking_test_task.vehicle.entity.dto.TicketDto;
import com.example.parking_test_task.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/check-in")
    public ResponseEntity<TicketDto> checkInVehicle(@RequestBody CheckInDto checkInDto){
        return ResponseEntity.ok(vehicleService.checkIn(checkInDto));
    }

    @PostMapping("/check-out/{licencePlate}")
    public ResponseEntity<CheckOutDto> checkOutVehicle(@PathVariable String licencePlate){
        return ResponseEntity.ok(vehicleService.checkOut(licencePlate));
    }
}
