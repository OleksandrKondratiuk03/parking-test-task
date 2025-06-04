package com.example.parking_test_task.slot;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.floor.repo.FloorRepository;
import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.slot.entity.ParkingSlot;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotRequestDto;
import com.example.parking_test_task.slot.entity.dto.ParkingSlotResponseDto;
import com.example.parking_test_task.slot.entity.enums.Type;
import com.example.parking_test_task.slot.repo.ParkingSlotRepository;
import com.example.parking_test_task.slot.service.impl.ParkingSlotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ParkingSlotServiceImplTest {

    @Mock
    private ParkingSlotRepository parkingSlotRepository;

    @Mock
    private FloorRepository floorRepository;

    @InjectMocks
    private ParkingSlotServiceImpl parkingSlotService;

    @Test
    void createParkingSlotTest_BadRequest(){
        ParkingSlotRequestDto parkingSlotRequestDto = new ParkingSlotRequestDto();
        parkingSlotRequestDto.setFloorId(1L);
        parkingSlotRequestDto.setType(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> parkingSlotService.createParkingSlot(parkingSlotRequestDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("ALL_FIELDS_REQUIRED", exception.getReason());
    }

    @Test
    void createParkingSlotTest_LastSlotNumberNull(){
        ParkingSlotRequestDto parkingSlotRequestDto = new ParkingSlotRequestDto();
        parkingSlotRequestDto.setFloorId(1L);
        parkingSlotRequestDto.setType(Type.COMPACT);

        Floor floor = Floor.builder()
                .id(1L)
                .parkingLot(new ParkingLot())
                .slots(new ArrayList<>())
                .floorNumber(1)
                .build();

        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(1L)
                .slotNumber(1)
                .available(true)
                .parkingRecord(new ArrayList<>())
                .type(Type.COMPACT)
                .floor(floor)
                .build();

        when(parkingSlotRepository.getLastSlotNumber(any())).thenReturn(null);
        when(floorRepository.findById(any())).thenReturn(Optional.of(floor));
        when(parkingSlotRepository.save(any())).thenReturn(parkingSlot);

        ParkingSlotResponseDto result = parkingSlotService.createParkingSlot(parkingSlotRequestDto);

        assertNotNull(result);
        assertEquals(parkingSlotRequestDto.getFloorId(), result.getFloorId());
        assertEquals(1, result.getSlotNumber());
        assertEquals(parkingSlotRequestDto.getType(), result.getType());
        assertEquals(parkingSlot.getId(), result.getId());

        verify(parkingSlotRepository, times(1)).getLastSlotNumber(any());
        verify(floorRepository, times(1)).findById(any());
        verify(parkingSlotRepository, times(1)).save(any());
    }


    @Test
    void createParkingSlotTest_Success(){
        ParkingSlotRequestDto parkingSlotRequestDto = new ParkingSlotRequestDto();
        parkingSlotRequestDto.setFloorId(1L);
        parkingSlotRequestDto.setType(Type.COMPACT);

        Floor floor = Floor.builder()
                .id(1L)
                .parkingLot(new ParkingLot())
                .slots(new ArrayList<>())
                .floorNumber(1)
                .build();

        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(1L)
                .slotNumber(2)
                .available(true)
                .parkingRecord(new ArrayList<>())
                .type(Type.COMPACT)
                .floor(floor)
                .build();

        when(parkingSlotRepository.getLastSlotNumber(any())).thenReturn(1);
        when(floorRepository.findById(any())).thenReturn(Optional.of(floor));
        when(parkingSlotRepository.save(any())).thenReturn(parkingSlot);

        ParkingSlotResponseDto result = parkingSlotService.createParkingSlot(parkingSlotRequestDto);

        assertNotNull(result);
        assertEquals(parkingSlotRequestDto.getFloorId(), result.getFloorId());
        assertEquals(2, result.getSlotNumber());
        assertEquals(parkingSlotRequestDto.getType(), result.getType());
        assertEquals(parkingSlot.getId(), result.getId());

        verify(parkingSlotRepository, times(1)).getLastSlotNumber(any());
        verify(floorRepository, times(1)).findById(any());
        verify(parkingSlotRepository, times(1)).save(any());
    }


    @Test
    void deleteParkingSlotTest(){
        Long slotId = 1L;

        doNothing().when(parkingSlotRepository).deleteById(slotId);

        parkingSlotService.deleteParkingSlot(slotId);

        verify(parkingSlotRepository, times(1)).deleteById(slotId);
    }


    @Test
    void changeSlotAvailabilityTest(){
        Long slotId = 1L;


        Floor floor = Floor.builder()
                .id(1L)
                .parkingLot(new ParkingLot())
                .slots(new ArrayList<>())
                .floorNumber(1)
                .build();

        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(1L)
                .slotNumber(2)
                .available(true)
                .parkingRecord(new ArrayList<>())
                .type(Type.COMPACT)
                .floor(floor)
                .build();

        ParkingSlot parkingSlot2 = ParkingSlot.builder()
                .id(1L)
                .slotNumber(2)
                .available(false)
                .parkingRecord(new ArrayList<>())
                .type(Type.COMPACT)
                .floor(floor)
                .build();

        when(parkingSlotRepository.findById(slotId)).thenReturn(Optional.of(parkingSlot));
        when(parkingSlotRepository.save(any())).thenReturn(parkingSlot2);

        ParkingSlotResponseDto result = parkingSlotService.changeAvailable(slotId);

        assertNotNull(result);
        assertFalse(result.isAvailable());
    }
}
