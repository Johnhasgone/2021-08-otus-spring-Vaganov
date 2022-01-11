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
    title varchar(200)
);

create table if not exists book_author(
    book_id bigint,
    author_id bigint,

    constraint fk_book_author_book
        foreign key (book_id)
        references book(id)
        on delete cascade,

    constraint fk_author
        foreign key (author_id)
        references author(id)
        on delete restrict
);

create table if not exists book_genre(
    book_id bigint,
    genre_id bigint,

    constraint fk_book_genre_book
        foreign key (book_id)
        references book(id)
        on delete cascade,

    constraint fk_genre
        foreign key (genre_id)
        references genre(id)
        on delete restrict
);