-- liquibase formatted sql

--changeset johnhasgone:2022-02-21-001-music
create table if not exists track(
    id bigserial primary key,
    title varchar(1000) not null
);

create table if not exists artist(
    id bigserial primary key,
    name varchar(100) not null
);

create table if not exists genre(
    id bigserial primary key,
    name varchar(100)
);

create table if not exists playlist(
    id bigserial primary key,
    title varchar(1000)
);

create table if not exists track_artist(
    track_id bigint,
    artist_id bigint,

    constraint fk_track_artist_track foreign key (track_id) references track(id) on delete cascade,
    constraint fk_track_artist_artist foreign key (artist_id) references artist(id) on delete restrict
);

create table if not exists playlist_track(
    playlist_id bigint,
    track_id bigint,

    constraint fk_playlist_track_playlist foreign key (playlist_id) references playlist(id) on delete cascade,
    constraint fk_playlist_track_track foreign key (track_id) references track(id) on delete cascade
);