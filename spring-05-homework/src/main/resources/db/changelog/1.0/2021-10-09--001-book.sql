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

--changeset johnhasgone:2021-10-09-002-data
insert into genre(name) values
('басня'),
('аполог'),
('былина'),
('баллада'),
('миф'),
('новелла'),
('повесть'),
('рассказ'),
('роман'),
('роман-эпопея'),
('сказка'),
('эпопея')
;

insert into author(name) values
('Джейн Остин'),
('Джордж Оруэлл'),
('Шарлотта Бронте'),
('Джозеф Хеллер'),
('Эмили Бронте'),
('Джером Сэлинджер'),
('Чарльз Диккенс'),
('Лев Толстой'),
('Джоан Роулинг'),
('Льюис Кэрролл'),
('Габриэль Гарсиа Маркес'),
('Роберт Стивенсон'),
('Александр Дюма'),
('Фёдор Достоевский')
;

insert into book(name, author_id, genre_id)  values
('Гордость и предубеждение', 1, 9),
('1984', 2, 9),
('Джейн Эйр', 3, 9),
('Уловка-22', 4, 9),
('Грозовой перевал', 5, 9),
('Над пропастью во ржи', 6, 7),
('Большие надежды', 7, 9),
('Война и мир', 8, 10),
('Гарри Поттер и философский камень', 9, 10),
('Алиса в Стране чудес', 10, 11),
('Сто лет одиночества', 11, 9),
('Дэвид Копперфильд', 7, 9),
('Остров сокровищ', 12, 9),
('Доводы рассудка', 1, 9),
('Эмма', 1, 9),
('Граф Монте-Кристо', 13, 9),
('Скотный двор', 2, 7),
('Рождественская песнь', 7, 9),
('Анна Каренина', 8, 9),
('Преступление и наказание', 14, 9),
('Повесть о двух городах', 7, 9),
('Холодный дом', 7, 9),
('Любовь во время холеры', 11, 9),
('Посмертные записки Пиквикского клуба', 7, 9),
('Приключения Оливера Твиста', 7, 9),
('Бесы', 14, 9),
('Идиот', 14, 9),
('Вечный муж', 14, 7)
;

