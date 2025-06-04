package com.example.parking_test_task.parkingrecord.controller;

import com.example.parking_test_task.parkingrecord.entity.dto.ParkingRecordResponseDto;
import com.example.parking_test_task.parkingrecord.service.ParkingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class ParkingRecordController {

    private final ParkingRecordService parkingRecordService;

    @GetMapping("/{lotId}")
    public ResponseEntity<List<ParkingRecordResponseDto>> getRecords(@PathVariable Long lotId){
        return ResponseEntity.ok(parkingRecordService.getAllRecords(lotId));
    }
}
