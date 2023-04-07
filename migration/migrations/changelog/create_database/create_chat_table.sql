--liquibase formatted sql

--changeset create_database:1
create table if not exists chat
(
    id BIGINT primary key
);