package com.example.parking_test_task.vehicle.entity.dto;

import com.example.parking_test_task.vehicle.entity.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInDto {
    private VehicleType vehicleType;
    private String licencePlate;
}
