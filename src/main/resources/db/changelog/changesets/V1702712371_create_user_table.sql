-- liquibase formatted sql
-- changeset gnori-zon:V1702712371_create_user_table

CREATE TABLE bot_users (
    id         BIGSERIAL PRIMARY KEY,
    chat_id    BIGINT UNIQUE,
    username   VARCHAR,
    is_admin   BOOLEAN                  NOT NULL DEFAULT false,
    is_active  BOOLEAN                  NOT NULL DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE
)