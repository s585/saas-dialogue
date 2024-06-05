-- liquibase formatted sql

--changeset asvetov:20240308_dialogue

CREATE TABLE dialogue
(
    id           UUID NOT NULL DEFAULT uuid_generate_v1(),
    participants UUID[],
    created_date TIMESTAMP WITH TIME ZONE,
    updated_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);