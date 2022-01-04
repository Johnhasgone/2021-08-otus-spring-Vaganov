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


create table if not exists comment(
    id bigint auto_increment primary key,
    text varchar(2048),
    book_id bigint,
    constraint fk_book
        foreign key (book_id)
            references book(id)
            on delete cascade
);

--changeset johnhasgone:2021-12-28-001-user
create table if not exists user (
    id bigint auto_increment primary key,
    username varchar(2048) not null unique,
    password varchar(2048),
    enabled boolean default true,
    authority varchar(2048)
)