-- liquibase formatted sql
-- changeset gnori-zon:V1702712410_create_characteristic_opposites_table

CREATE TABLE characteristic_opposites (
    characteristic_id BIGINT REFERENCES characteristics (id),
    opposite_characteristic_id BIGINT REFERENCES characteristics (id),
    PRIMARY KEY (characteristic_id, opposite_characteristic_id)
)