-- liquibase formatted sql
-- changeset gnori-zon:V1702712400_create_characteristics_table

CREATE TABLE characteristics (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR NOT NULL UNIQUE,
    image_url VARCHAR,
    type_id BIGINT REFERENCES characteristic_types (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE
)