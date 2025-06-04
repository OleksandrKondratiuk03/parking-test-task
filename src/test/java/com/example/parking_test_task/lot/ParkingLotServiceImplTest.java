package com.example.parking_test_task.lot;

import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.lot.entity.dto.ParkingLotRequestDto;
import com.example.parking_test_task.lot.entity.dto.ParkingLotResponseDto;
import com.example.parking_test_task.lot.repo.ParkingLotRepository;
import com.example.parking_test_task.lot.service.impl.ParkingLotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ParkingLotServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    @Test
    void createParkingLotTest_BadRequest() {
        ParkingLotRequestDto parkingLotRequestDto = new ParkingLotRequestDto();
        parkingLotRequestDto.setName(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> parkingLotService.createParkingLot(parkingLotRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("ALL_FIELDS_REQUIRED", exception.getReason());
    }

    @Test
    void createParkingLotTest_Success(){
        ParkingLotRequestDto parkingLotRequestDto = new ParkingLotRequestDto();
        parkingLotRequestDto.setName("NAME");

        ParkingLot parkingLot = ParkingLot.builder()
                .id(1L)
                .name("NAME")
                .floors(new ArrayList<>())
                .build();

        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        ParkingLotResponseDto result = parkingLotService.createParkingLot(parkingLotRequestDto);

        assertNotNull(result);
        assertEquals("NAME", result.getName());

        verify(parkingLotRepository, times(1)).save(any());
    }

    @Test
    void deleteParkingLotTest_Success(){
        Long lotId = 1L;

        doNothing().when(parkingLotRepository).deleteById(lotId);

        parkingLotService.deleteParkingLot(lotId);

        verify(parkingLotRepository, times(1)).deleteById(lotId);
    }
}
