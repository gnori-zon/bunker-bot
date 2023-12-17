-- liquibase formatted sql
-- changeset gnori-zon:V1702712420_insert_characteristic_types_table

INSERT INTO characteristic_types (name)
    VALUES ('Образование'),
           ('Опыт'),
           ('Пол'),
           ('Национальность'),
           ('Особеннсоть')