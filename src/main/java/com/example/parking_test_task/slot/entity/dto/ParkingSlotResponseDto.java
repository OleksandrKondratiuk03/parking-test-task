package com.example.parking_test_task.slot.entity.dto;

import com.example.parking_test_task.slot.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSlotResponseDto {
    private Long id;

    private Integer slotNumber;

    private boolean available;

    private Type type;

    private Long floorId;

}
