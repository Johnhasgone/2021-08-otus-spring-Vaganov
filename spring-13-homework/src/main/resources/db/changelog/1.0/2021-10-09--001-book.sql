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
);

--changeset johnhasgone:2022-01-05-001-acl
create table if not exists acl_sid (
    id bigint auto_increment primary key,
    sid varchar(100) not null unique,
    principal tinyint(1)
);

create table if not exists acl_class (
    id bigint auto_increment primary key,
    class varchar(255) not null unique
);

create table if not exists acl_object_identity (
    id bigint auto_increment primary key,
    object_id_class bigint not null,
    object_id_identity bigint not null,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting tinyint(1) not null,
    constraint fk_object_id_class
        foreign key (object_id_class)
            references acl_class(id),
    constraint fk_parent_object
        foreign key (parent_object)
            references acl_object_identity(id),
    constraint fk_owner_sid
        foreign key (owner_sid)
            references acl_sid(id),
    constraint fk_object_id_identity
        foreign key (object_id_identity)
            references book(id) on delete cascade
);

create table if not exists acl_entry (
    id bigint auto_increment primary key,
    acl_object_identity bigint not null,
    ace_order int not null,
    sid bigint not null,
    mask int not null,
    granting tinyint(1) not null,
    audit_success tinyint(1) not null,
    audit_failure tinyint(1) not null,
    constraint fk_acl_object_identity
        foreign key (acl_object_identity)
            references acl_object_identity(id) on delete cascade,
    constraint fk_sid
        foreign key (sid)
            references acl_sid(id)
);