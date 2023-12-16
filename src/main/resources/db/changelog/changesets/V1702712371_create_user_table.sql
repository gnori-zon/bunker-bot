-- liquibase formatted sql
-- changeset gnori-zon:V1702712371_create_user_table

CREATE TABLE bot_user (
    chat_id    BIGSERIAL PRIMARY KEY,
    username   VARCHAR,
    is_admin   BOOLEAN NOT NULL DEFAULT false,
    is_blocked BOOLEAN NOT NULL DEFAULT false
)