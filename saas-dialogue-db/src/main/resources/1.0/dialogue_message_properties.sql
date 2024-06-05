-- liquibase formatted sql

--changeset asvetov:20240512_dialogue_message_properties
CREATE TABLE dialogue_message_state
(
    id           UUID NOT NULL DEFAULT uuid_generate_v1(),
    dialogue_id  UUID NOT NULL,
    message_id   UUID NOT NULL,
    recipient_id UUID NOT NULL,
    read         BOOLEAN,
    PRIMARY KEY (id),
    UNIQUE (dialogue_id, message_id, recipient_id)
);