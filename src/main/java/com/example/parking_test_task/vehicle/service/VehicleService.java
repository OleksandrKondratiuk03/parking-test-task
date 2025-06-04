package com.example.parking_test_task.vehicle.service;

import com.example.parking_test_task.vehicle.entity.dto.CheckInDto;
import com.example.parking_test_task.vehicle.entity.dto.CheckOutDto;
import com.example.parking_test_task.vehicle.entity.dto.TicketDto;

public interface VehicleService {
    TicketDto checkIn(CheckInDto checkInDto);

    CheckOutDto checkOut(String licencePlate);
}
