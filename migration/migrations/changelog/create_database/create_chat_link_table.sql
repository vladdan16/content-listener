--liquibase formatted sql

--changeset create_database:1
create table if not exists chat_link
(
    chat_id bigint,
    link_id bigint,
    primary key (chat_id, link_id),
    foreign key (chat_id) references chat (id),
    foreign key (link_id) references link (id)
);