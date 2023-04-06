--liquibase formatted sql

create table if not exists link_type
(
    id   integer primary key,
    type varchar(255) not null
);