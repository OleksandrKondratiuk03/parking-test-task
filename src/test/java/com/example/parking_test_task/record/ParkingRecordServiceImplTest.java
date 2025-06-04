package com.example.parking_test_task.record;

import com.example.parking_test_task.floor.entity.Floor;
import com.example.parking_test_task.lot.entity.ParkingLot;
import com.example.parking_test_task.parkingrecord.entity.ParkingRecord;
import com.example.parking_test_task.parkingrecord.entity.dto.ParkingRecordResponseDto;
import com.example.parking_test_task.parkingrecord.repo.ParkingRecordRepository;
import com.example.parking_test_task.parkingrecord.service.impl.ParkingRecordServiceImpl;
import com.example.parking_test_task.slot.entity.ParkingSlot;
import com.example.parking_test_task.slot.entity.enums.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ParkingRecordServiceImplTest {

    @Mock
    private ParkingRecordRepository parkingRecordRepository;

    @InjectMocks
    private ParkingRecordServiceImpl parkingRecordService;

    @Test
    void getAllParkingRecordTest_Success(){
        Long lotId=1L;

        Floor floor = Floor.builder()
                .parkingLot(new ParkingLot())
                .floorNumber(2)
                .slots(new ArrayList<>())
                .id(1L)
                .build();
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(1L)
                .type(Type.COMPACT)
                .slotNumber(1)
                .available(true)
                .floor(floor)
                .build();

        ParkingRecord parkingRecord = ParkingRecord.builder()
                .id(2L)
                .parkingSlot(parkingSlot)
                .licencePlate("AB1234CD")
                .entryTime(LocalDateTime.now().minusHours(2))
                .exitTime(LocalDateTime.now())
                .build();

        ParkingRecord parkingRecord2 = ParkingRecord.builder()
                .id(1L)
                .parkingSlot(parkingSlot)
                .licencePlate("AB1656MB")
                .entryTime(LocalDateTime.now())
                .exitTime(null)
                .build();

        when(parkingRecordRepository.findAllByParkingLot(lotId)).thenReturn(List.of(parkingRecord, parkingRecord2));

        List<ParkingRecordResponseDto> result = parkingRecordService.getAllRecords(lotId);

        assertNotNull(result);
        assertEquals(parkingRecord.getLicencePlate(), result.get(0).getLicencePlate());
        assertEquals(parkingRecord2.getLicencePlate(), result.get(1).getLicencePlate());

        verify(parkingRecordRepository, times(1)).findAllByParkingLot(lotId);
    }
}
