CREATE TABLE
    SLOTS
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    slot_number INTEGER     NOT NULL,
    available   BOOLEAN     NOT NULL,
    type        VARCHAR(50) NOT NULL,
    floor_id    BIGINT      NOT NULL,
        CONSTRAINT fk_floor_slot
        FOREIGN KEY (floor_id) REFERENCES FLOORS (id)
);