package com.example.parking_test_task.vehicle.fee.strategy.impl;

import com.example.parking_test_task.slot.entity.enums.Type;
import com.example.parking_test_task.vehicle.fee.strategy.FeeCalculationStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MotorcycleFeeCalculationStrategyImpl implements FeeCalculationStrategy {

    private static final double BASIC_FEE = 1.0;

    @Override
    public boolean supports(Type type) {
        return Type.MOTORCYCLE.equals(type);
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry,exit).toMinutes();
        return ((double) minutes /60)*BASIC_FEE;
    }
}
