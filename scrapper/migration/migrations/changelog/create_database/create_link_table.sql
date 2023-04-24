--liquibase formatted sql

--changeset create_database:1
create sequence if not exists link_sequence start 1;
create table if not exists link
(
    id           bigint default nextval('link_sequence') primary key,
    link         varchar unique,
    time_created timestamp,
    time_checked timestamp,
    updated_at   timestamp
);