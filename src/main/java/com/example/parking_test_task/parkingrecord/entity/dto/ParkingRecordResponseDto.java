package com.example.parking_test_task.parkingrecord.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRecordResponseDto {

    private String licencePlate;

    private Integer floorNumber;

    private Integer slotNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;
}
