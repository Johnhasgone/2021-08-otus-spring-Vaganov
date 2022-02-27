-- liquibase formatted sql

--changeset johnhasgone:2022-02-21-001-track
create table if not exists track(
    id bigserial primary key,
    track_id bigint not null,
    album_id bigint not null,
    artist_id bigint not null
);

create table if not exists genre(
    id bigserial primary key,
    name varchar(100)
);

create table if not exists track_genre(
    track_id bigint not null,
    genre_id bigint not null,

    constraint fk_track_genre_track foreign key (track_id) references track(id) on delete cascade,
    constraint fk_track_genre_genre foreign key (genre_id) references genre(id) on delete restrict
);