package com.example.parking_test_task.slot.entity.dto;

import com.example.parking_test_task.slot.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSlotRequestDto {
    private Type type;
    private Long floorId;
}
