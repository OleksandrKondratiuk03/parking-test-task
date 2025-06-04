package com.example.parking_test_task.floor;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.floor.entity.dto.FloorRequestDto;
import com.example.parking_test_task.floor.entity.dto.FloorResponseDto;
import com.example.parking_test_task.floor.repo.FloorRepository;
import com.example.parking_test_task.floor.service.impl.FloorServiceImpl;
import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.lot.repo.ParkingLotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FloorServiceImplTest {

    @Mock
    private FloorRepository floorRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private FloorServiceImpl floorService;

    @Test
    void createFloorTest_BadRequest(){
        FloorRequestDto floorRequestDto = new FloorRequestDto();
        floorRequestDto.setParkingLotId(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> floorService.createFloor(floorRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("ALL_FIELDS_REQUIRED", exception.getReason());
    }

    @Test
    void createFloorTest_LastFloorNumberNull(){
        FloorRequestDto floorRequestDto = new FloorRequestDto();
        floorRequestDto.setParkingLotId(1L);

        ParkingLot parkingLot = ParkingLot.builder()
                .id(1L)
                .name("NAME")
                .floors(List.of()).build();

        Floor floor = Floor.builder()
                .id(1L)
                .floorNumber(1)
                .slots(new ArrayList<>())
                .parkingLot(parkingLot)
                .build();

        when(floorRepository.getLastFloorNumber(1L)).thenReturn(null);
        when(parkingLotRepository.findById(any())).thenReturn(Optional.of(parkingLot));
        when(floorRepository.save(any())).thenReturn(floor);

        FloorResponseDto result = floorService.createFloor(floorRequestDto);

        assertNotNull(result);
        assertEquals(result.getFloorNumber(), 1);
        verify(floorRepository, times(1)).getLastFloorNumber(1L);
        verify(parkingLotRepository, times(1)).findById(any());
        verify(floorRepository, times(1)).save(any());

    }

    @Test
    void createFloorTest_Success(){
        FloorRequestDto floorRequestDto = new FloorRequestDto();
        floorRequestDto.setParkingLotId(1L);

        ParkingLot parkingLot = ParkingLot.builder()
                .id(1L)
                .name("NAME")
                .floors(List.of()).build();

        Floor floor = Floor.builder()
                .id(1L)
                .floorNumber(2)
                .slots(new ArrayList<>())
                .parkingLot(parkingLot)
                .build();

        when(floorRepository.getLastFloorNumber(1L)).thenReturn(1);
        when(parkingLotRepository.findById(any())).thenReturn(Optional.of(parkingLot));
        when(floorRepository.save(any())).thenReturn(floor);

        FloorResponseDto result = floorService.createFloor(floorRequestDto);

        assertNotNull(result);
        assertEquals(2, result.getFloorNumber());
        verify(floorRepository, times(1)).getLastFloorNumber(1L);
        verify(parkingLotRepository, times(1)).findById(any());
        verify(floorRepository, times(1)).save(any());
    }

    @Test
    void deleteFloorTest(){
        Long floorId = 1L;

        doNothing().when(floorRepository).deleteById(floorId);

        floorService.deleteFloor(floorId);

        verify(floorRepository, times(1)).deleteById(floorId);
    }
}
