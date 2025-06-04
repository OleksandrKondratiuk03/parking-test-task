package com.example.parking_test_task.floor.controller;

import com.example.parking_test_task.floor.entity.dto.FloorRequestDto;
import com.example.parking_test_task.floor.entity.dto.FloorResponseDto;
import com.example.parking_test_task.floor.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;


    @PostMapping
    public ResponseEntity<FloorResponseDto> createFloor(@RequestBody FloorRequestDto floorRequestDto){
        return ResponseEntity.ok(floorService.createFloor(floorRequestDto));
    }

    @DeleteMapping("/{floorId}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long floorId){
        floorService.deleteFloor(floorId);
        return ResponseEntity.ok().build();
    }
}
