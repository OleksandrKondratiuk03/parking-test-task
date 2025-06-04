package com.example.parking_test_task.vehicle.service.impl;

import com.example.parking_test_task.parkingrecord.entity.ParkingRecord;
import com.example.parking_test_task.parkingrecord.repo.ParkingRecordRepository;
import com.example.parking_test_task.slot.entity.ParkingSlot;
import com.example.parking_test_task.slot.entity.enums.Type;
import com.example.parking_test_task.slot.repo.ParkingSlotRepository;
import com.example.parking_test_task.vehicle.entity.dto.CheckInDto;
import com.example.parking_test_task.vehicle.entity.dto.CheckOutDto;
import com.example.parking_test_task.vehicle.entity.dto.TicketDto;
import com.example.parking_test_task.vehicle.entity.enums.VehicleType;
import com.example.parking_test_task.vehicle.fee.factory.FeeStrategyFactory;
import com.example.parking_test_task.vehicle.fee.strategy.FeeCalculationStrategy;
import com.example.parking_test_task.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingRecordRepository parkingRecordRepository;
    private final FeeStrategyFactory feeStrategyFactory;

    private Type mapVehicleTypeToSlots(VehicleType vehicleType) {
        return switch (vehicleType) {
            case CAR -> Type.COMPACT;
            case TRUCK -> Type.LARGE;
            case MOTORCYCLE -> Type.MOTORCYCLE;
        };
    }

    @Override
    @Transactional
    public TicketDto checkIn(CheckInDto checkInDto) {
        Type type = mapVehicleTypeToSlots(checkInDto.getVehicleType());
        ParkingSlot parkingSlot = parkingSlotRepository.findAvailableSlots(type);
        parkingSlot.setAvailable(false);
        parkingSlotRepository.save(parkingSlot);

        LocalDateTime entryTime = LocalDateTime.now();

        ParkingRecord parkingRecord = ParkingRecord.builder()
                .entryTime(entryTime)
                .licencePlate(checkInDto.getLicencePlate())
                .parkingSlot(parkingSlot)
                .build();
        parkingRecordRepository.save(parkingRecord);

        return TicketDto.builder()
                .licencePlate(checkInDto.getLicencePlate())
                .entryTime(entryTime)
                .level(parkingSlot.getFloor().getFloorNumber())
                .slot(parkingSlot.getSlotNumber())
                .build();
    }

    @Override
    public CheckOutDto checkOut(String licencePlate) {
        ParkingRecord parkingRecord = parkingRecordRepository.findByLicencePlate(licencePlate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_RECORD_FOUND"));

        LocalDateTime exitTime = LocalDateTime.now();

        FeeCalculationStrategy feeCalculationStrategy = feeStrategyFactory.getStrategy(parkingRecord.getParkingSlot().getType());
        double fee = feeCalculationStrategy.calculateFee(parkingRecord.getEntryTime(), exitTime);

        ParkingSlot parkingSlot =parkingRecord.getParkingSlot();
        parkingSlot.setAvailable(true);
        parkingSlotRepository.save(parkingSlot);

        parkingRecord.setExitTime(exitTime);
        parkingRecordRepository.save(parkingRecord);

        return CheckOutDto.builder()
                .entryTime(parkingRecord.getEntryTime())
                .exitTime(exitTime)
                .fee(fee)
                .build();
    }
}
