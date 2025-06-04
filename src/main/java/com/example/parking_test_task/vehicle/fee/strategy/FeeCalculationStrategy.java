package com.example.parking_test_task.vehicle.fee.strategy;

import com.example.parking_test_task.slot.entity.enums.Type;

import java.time.LocalDateTime;

public interface FeeCalculationStrategy {

    boolean supports(Type type);

    double calculateFee(LocalDateTime entry, LocalDateTime exit);
}
