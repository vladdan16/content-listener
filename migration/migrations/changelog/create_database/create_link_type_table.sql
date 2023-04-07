--liquibase formatted sql

--changeset create_database:1
create sequence if not exists link_type_sequence start 1;
create table if not exists link_type
(
    id   integer default nextval('link_type_sequence') primary key,
    type varchar(255) not null
);