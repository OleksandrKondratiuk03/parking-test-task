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
public class TicketDto {

    private String licencePlate;
    private LocalDateTime entryTime;
    private int level;
    private int slot;
}
