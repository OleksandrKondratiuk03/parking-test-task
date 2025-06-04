CREATE TABLE
    RECORDS
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    licence_plate VARCHAR(10) NOT NULL,
    slot_id BIGINT NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    CONSTRAINT fk_parking_slot_record
        FOREIGN KEY (slot_id) REFERENCES SLOTS(id)
);