--liquibase formatted sql

--changeset create_database:1
create table if not exists link_type
(
    id   integer primary key,
    type varchar(255) not null
);