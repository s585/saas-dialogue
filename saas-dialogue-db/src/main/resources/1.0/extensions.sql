-- liquibase formatted sql

--changeset asvetov:20240103_enable_uuid_ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
