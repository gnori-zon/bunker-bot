-- liquibase formatted sql
-- changeset gnori-zon:V1702712380_alter_table_user_add_column

ALTER TABLE bot_users ADD COLUMN state VARCHAR NOT NULL DEFAULT 'DEFAULT';