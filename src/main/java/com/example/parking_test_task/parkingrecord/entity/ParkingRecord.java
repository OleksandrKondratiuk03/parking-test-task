package com.example.parking_test_task.parkingrecord.entity;

import com.example.parking_test_task.slot.entity.ParkingSlot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class ParkingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licencePlate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "slot_id")
    private ParkingSlot parkingSlot;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;
}
