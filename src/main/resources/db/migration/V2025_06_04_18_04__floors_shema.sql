CREATE TABLE
    FLOORS
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    floor_number   INTEGER NOT NULL,
    parking_lot_id BIGINT  NOT NULL,
    CONSTRAINT fk_parking_lot_floor
        FOREIGN KEY (parking_lot_id) REFERENCES LOTS (id)
);