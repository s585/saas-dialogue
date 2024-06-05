-- liquibase formatted sql

--changeset asvetov:20240308_dialogue_message
CREATE TABLE dialogue_message
(
    id           UUID NOT NULL DEFAULT uuid_generate_v1(),
    dialogue_id  UUID NOT NULL,
    author_id    UUID NOT NULL,
    recipient_id UUID NOT NULL,
    payload      TEXT,
    created_date TIMESTAMP WITH TIME ZONE,
    updated_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);