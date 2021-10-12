-- liquibase formatted sql

--changeset johnhasgone:2021-10-09-001-book
create table if not exists author(
    id bigint auto_increment primary key,
    name varchar(200)
);

create table if not exists  genre(
    id bigint auto_increment primary key,
    name varchar(200)
);

create table if not exists book(
    id bigint auto_increment primary key,
    name varchar(200),
    author_id bigint,
    genre_id bigint,
    constraint fk_author
        foreign key (author_id)
            references author(id)
            on delete set null,
    constraint fk_genre
        foreign key (genre_id)
            references genre(id)
            on delete set null
);