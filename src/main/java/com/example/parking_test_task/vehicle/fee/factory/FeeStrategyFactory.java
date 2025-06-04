package com.example.parking_test_task.vehicle.fee.factory;

import com.example.parking_test_task.slot.entity.enums.Type;
import com.example.parking_test_task.vehicle.fee.strategy.FeeCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeeStrategyFactory {

    private final List<FeeCalculationStrategy> strategies;

    public FeeCalculationStrategy getStrategy(Type type){
        return strategies.stream()
                .filter(strategy -> strategy.supports(type))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_SUITABLE_CALCULATION_STRATEGY"));
    }
}
