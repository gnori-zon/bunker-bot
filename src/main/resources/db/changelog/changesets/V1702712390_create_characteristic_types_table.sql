-- liquibase formatted sql
-- changeset gnori-zon:V1702712390_create_characteristic_types_table

CREATE TABLE characteristic_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE
)