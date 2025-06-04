package com.example.parking_test_task.vehicle.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutDto {
    private long minutesDuration;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;
}
