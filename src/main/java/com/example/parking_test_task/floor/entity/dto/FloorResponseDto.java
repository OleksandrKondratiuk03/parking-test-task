package com.example.parking_test_task.floor.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FloorResponseDto {
    private Long id;
    private Integer floorNumber;
    private List<Long> parkingSlots;
    private Long parkingLotId;
}
