--liquibase formatted sql

--changeset create_database:1
create sequence if not exists link_sequence start 1;
create table if not exists link
(
    id           bigint default nextval('link_sequence') primary key,
    link         varchar(255) not null,
    owner_id     bigint       not null,
    foreign key (owner_id) references chat (id)
);