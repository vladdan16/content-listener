--liquibase formatted sql

--changeset create_database:1
create sequence if not exists link_sequence start 1;
create table if not exists link
(
    id           bigint default nextval('link_sequence') primary key,
    link         varchar(255) not null,
    link_type_id integer      not null,
    owner_id     bigint       not null,
    foreign key (owner_id) references chat (id),
    foreign key (link_type_id) references link_type (id)
);