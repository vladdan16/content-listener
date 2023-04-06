--liquibase formatted sql


create table if not exists chat
(
    id BIGINT primary key
);