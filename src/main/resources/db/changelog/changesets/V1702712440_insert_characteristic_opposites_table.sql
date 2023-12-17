-- liquibase formatted sql
-- changeset gnori-zon:V1702712440_insert_characteristic_opposites_table

INSERT INTO characteristic_opposites (characteristic_id, opposite_characteristic_id)
    VALUES (81, 55),
           (55, 81),
           (81, 56),
           (56, 81),
           (45, 71),
           (71, 45),
           (58, 44),
           (44, 58),
           (50, 30),
           (30, 50),
           (72, 48),
           (48, 72),
           (41, 71),
           (71, 41),
           (70, 67),
           (67, 70),
           (51, 35),
           (35, 51),
           (42, 29),
           (29, 42)
