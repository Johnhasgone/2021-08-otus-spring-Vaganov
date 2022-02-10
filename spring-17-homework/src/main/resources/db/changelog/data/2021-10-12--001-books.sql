-- liquibase formatted sql

--changeset johnhasgone:2021-10-12-001-books
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
                                 ('Фёдор Достоевский'),
                                 ('Аркадий Стругацкий'),
                                 ('Борис Стругацкий')
;

insert into book(title)  values
                             ('Гордость и предубеждение'),
                             ('1984'),
                             ('Джейн Эйр'),
                             ('Уловка-22'),
                             ('Грозовой перевал'),
                             ('Над пропастью во ржи'),
                             ('Большие надежды'),
                             ('Война и мир'),
                             ('Гарри Поттер и философский камень'),
                             ('Алиса в Стране чудес'),
                             ('Сто лет одиночества'),
                             ('Дэвид Копперфильд'),
                             ('Остров сокровищ'),
                             ('Доводы рассудка'),
                             ('Эмма'),
                             ('Граф Монте-Кристо'),
                             ('Скотный двор'),
                             ('Рождественская песнь'),
                             ('Анна Каренина'),
                             ('Преступление и наказание'),
                             ('Повесть о двух городах'),
                             ('Холодный дом'),
                             ('Любовь во время холеры'),
                             ('Посмертные записки Пиквикского клуба'),
                             ('Приключения Оливера Твиста'),
                             ('Бесы'),
                             ('Идиот'),
                             ('Вечный муж'),
                             ('Понедельник начинается в субботу'),
                             ('Улитка на склоне')
;

insert into book_author(book_id, author_id) values
                                                   (1, 1),
                                                   (2, 2),
                                                   (3, 3),
                                                   (4, 4),
                                                   (5, 5),
                                                   (6, 6),
                                                   (7, 7),
                                                   (8, 8),
                                                   (9, 9),
                                                   (10, 10),
                                                   (11, 11),
                                                   (12, 7),
                                                   (13, 12),
                                                   (14, 1),
                                                   (15, 1),
                                                   (16, 13),
                                                   (17, 2),
                                                   (18, 7),
                                                   (19, 8),
                                                   (20, 14),
                                                   (21,7),
                                                   (22, 7),
                                                   (23, 11),
                                                   (24, 7),
                                                   (25, 7),
                                                   (26, 14),
                                                   (27, 14),
                                                   (28, 14),
                                                   (29, 15),
                                                   (29, 16),
                                                   (30, 15),
                                                   (30, 16)
;

insert into book_genre(book_id, genre_id) values
                                                 (1, 9),
                                                 (2, 9),
                                                 (3, 9),
                                                 (4, 9),
                                                 (5, 9),
                                                 (6, 7),
                                                 (7, 9),
                                                 (8, 10),
                                                 (9, 10),
                                                 (9, 11),
                                                 (9, 12),
                                                 (10, 11),
                                                 (11, 9),
                                                 (12, 9),
                                                 (13, 9),
                                                 (14, 9),
                                                 (15, 9),
                                                 (16, 9),
                                                 (17, 7),
                                                 (18, 9),
                                                 (19, 9),
                                                 (20, 9),
                                                 (21, 9),
                                                 (22, 9),
                                                 (23, 9),
                                                 (24, 9),
                                                 (25, 9),
                                                 (26, 9),
                                                 (27, 9),
                                                 (28, 7),
                                                 (29, 7),
                                                 (29, 11),
                                                 (30, 9),
                                                 (30, 11)
;

insert into comment(text, book_id) values
                                          ('круто!', 1),
                                          ('не читал', 2),
                                          ('пойдет', 2),
                                          ('неожиданный финал', 30),
                                          ('интересные герои', 30);

--changeset johnhasgone:2021-12-28-001-user
insert into library_user (username, password, authority) values
                            ('admin', '$2a$10$vIlwS97XFgdHi0dPaaCese/9ZF2u.jntA3I.dbHD0pVfFz/SX25.a', 'ROLE_ADMIN'),
                            ('teacher', '$2a$10$vIlwS97XFgdHi0dPaaCese/9ZF2u.jntA3I.dbHD0pVfFz/SX25.a', 'ROLE_TEACHER'),
                            ('student', '$2a$10$vIlwS97XFgdHi0dPaaCese/9ZF2u.jntA3I.dbHD0pVfFz/SX25.a', 'ROLE_STUDENT')
;

--changeset johnhasgone:2022-01-05-001-acl
insert into acl_sid (id, sid, principal) values
     (1, 'ROLE_ADMIN', 0),
     (2, 'ROLE_TEACHER', 0),
     (3, 'ROLE_STUDENT', 0)
;

insert into acl_class (id, class) values (1, 'ru.otus.spring17homework.domain.Book');

insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values ( 1, 1, 1, NULL, 1, 0 ),
       ( 2, 1, 2, NULL, 1, 0 ),
       ( 3, 1, 3, NULL, 1, 0 ),
       ( 4, 1, 4, NULL, 1, 0 ),
       ( 5, 1, 5, NULL, 1, 0 ),
       ( 6, 1, 6, NULL, 1, 0 ),
       ( 7, 1, 7, NULL, 1, 0 ),
       ( 8, 1, 8, NULL, 1, 0 ),
       ( 9, 1, 9, NULL, 1, 0 ),
       ( 10, 1, 10, NULL, 1, 0 ),
       ( 11, 1, 11, NULL, 1, 0 ),
       ( 12, 1, 12, NULL, 1, 0 ),
       ( 13, 1, 13, NULL, 1, 0 ),
       ( 14, 1, 14, NULL, 1, 0 ),
       ( 15, 1, 15, NULL, 1, 0 ),
       ( 16, 1, 16, NULL, 1, 0 ),
       ( 17, 1, 17, NULL, 1, 0 ),
       ( 18, 1, 18, NULL, 1, 0 ),
       ( 19, 1, 19, NULL, 1, 0 ),
       ( 20, 1, 20, NULL, 1, 0 ),
       ( 21, 1, 21, NULL, 1, 0 ),
       ( 22, 1, 22, NULL, 1, 0 ),
       ( 23, 1, 23, NULL, 1, 0 ),
       ( 24, 1, 24, NULL, 1, 0 ),
       ( 25, 1, 25, NULL, 1, 0 ),
       ( 26, 1, 26, NULL, 1, 0 ),
       ( 27, 1, 27, NULL, 1, 0 ),
       ( 28, 1, 28, NULL, 1, 0 ),
       ( 29, 1, 29, NULL, 1, 0 ),
       ( 30, 1, 30, NULL, 1, 0 )
;

insert into ACL_ENTRY (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
values
       ( 1, 1, 1, 1, 1, 1, 1, 1 ),
       ( 2, 1, 2, 1, 2, 1, 1, 1 ),
       ( 3, 1, 3, 2, 1, 1, 1, 1 ),
       ( 4, 1, 4, 3, 1, 1, 1, 1 ),

       ( 5, 2, 1, 1, 1, 1, 1, 1 ),
       ( 6, 2, 2, 1, 2, 1, 1, 1 ),
       ( 7, 2, 3, 2, 1, 1, 1, 1 ),

       ( 8, 3, 1, 1, 1, 1, 1, 1 ),
       ( 9, 3, 2, 1, 2, 1, 1, 1 ),
       ( 10, 3, 3, 2, 1, 1, 1, 1 ),
       ( 11, 3, 4, 3, 1, 1, 1, 1 ),

       ( 12, 4, 1, 1, 1, 1, 1, 1 ),
       ( 13, 4, 2, 1, 2, 1, 1, 1 ),
       ( 14, 4, 3, 2, 1, 1, 1, 1 ),

       ( 15, 5, 1, 1, 1, 1, 1, 1 ),
       ( 16, 5, 2, 1, 2, 1, 1, 1 ),
       ( 17, 5, 3, 2, 1, 1, 1, 1 ),
       ( 18, 5, 4, 3, 1, 1, 1, 1 ),

       ( 19, 6, 1, 1, 1, 1, 1, 1 ),
       ( 20, 6, 2, 1, 2, 1, 1, 1 ),
       ( 21, 6, 3, 2, 1, 1, 1, 1 ),

       ( 22, 7, 1, 1, 1, 1, 1, 1 ),
       ( 23, 7, 2, 1, 2, 1, 1, 1 ),
       ( 24, 7, 3, 2, 1, 1, 1, 1 ),
       ( 25, 7, 4, 3, 1, 1, 1, 1 ),

       ( 26, 8, 1, 1, 1, 1, 1, 1 ),
       ( 27, 8, 2, 1, 2, 1, 1, 1 ),
       ( 28, 8, 3, 2, 1, 1, 1, 1 ),

       ( 29, 9, 1, 1, 1, 1, 1, 1 ),
       ( 30, 9, 2, 1, 2, 1, 1, 1 ),
       ( 31, 9, 3, 2, 1, 1, 1, 1 ),
       ( 32, 9, 4, 3, 1, 1, 1, 1 ),

       ( 33, 10, 1, 1, 1, 1, 1, 1 ),
       ( 34, 10, 2, 1, 2, 1, 1, 1 ),
       ( 35, 10, 3, 2, 1, 1, 1, 1 ),

       ( 36, 11, 1, 1, 1, 1, 1, 1 ),
       ( 37, 11, 2, 1, 2, 1, 1, 1 ),
       ( 38, 11, 3, 2, 1, 1, 1, 1 ),
       ( 39, 11, 4, 3, 1, 1, 1, 1 ),

       ( 40, 12, 1, 1, 1, 1, 1, 1 ),
       ( 41, 12, 2, 1, 2, 1, 1, 1 ),
       ( 42, 12, 3, 2, 1, 1, 1, 1 ),

       ( 43, 13, 1, 1, 1, 1, 1, 1 ),
       ( 44, 13, 2, 1, 2, 1, 1, 1 ),
       ( 45, 13, 3, 2, 1, 1, 1, 1 ),
       ( 46, 13, 4, 3, 1, 1, 1, 1 ),

       ( 47, 14, 1, 1, 1, 1, 1, 1 ),
       ( 48, 14, 2, 1, 2, 1, 1, 1 ),
       ( 49, 14, 3, 2, 1, 1, 1, 1 ),

       ( 50, 15, 1, 1, 1, 1, 1, 1 ),
       ( 51, 15, 2, 1, 2, 1, 1, 1 ),
       ( 52, 15, 3, 2, 1, 1, 1, 1 ),
       ( 53, 15, 4, 3, 1, 1, 1, 1 ),

       ( 54, 16, 1, 1, 1, 1, 1, 1 ),
       ( 55, 16, 2, 1, 2, 1, 1, 1 ),
       ( 56, 16, 3, 2, 1, 1, 1, 1 ),

       ( 57, 17, 1, 1, 1, 1, 1, 1 ),
       ( 58, 17, 2, 1, 2, 1, 1, 1 ),
       ( 59, 17, 3, 2, 1, 1, 1, 1 ),
       ( 60, 17, 4, 3, 1, 1, 1, 1 ),

       ( 61, 18, 1, 1, 1, 1, 1, 1 ),
       ( 62, 18, 2, 1, 2, 1, 1, 1 ),
       ( 63, 18, 3, 2, 1, 1, 1, 1 ),

       ( 64, 19, 1, 1, 1, 1, 1, 1 ),
       ( 65, 19, 2, 1, 2, 1, 1, 1 ),
       ( 66, 19, 3, 2, 1, 1, 1, 1 ),
       ( 67, 19, 4, 3, 1, 1, 1, 1 ),

       ( 68, 20, 1, 1, 1, 1, 1, 1 ),
       ( 69, 20, 2, 1, 2, 1, 1, 1 ),
       ( 70, 20, 3, 2, 1, 1, 1, 1 ),

       ( 71, 21, 1, 1, 1, 1, 1, 1 ),
       ( 72, 21, 2, 1, 2, 1, 1, 1 ),
       ( 73, 21, 3, 2, 1, 1, 1, 1 ),
       ( 74, 21, 4, 3, 1, 1, 1, 1 ),

       ( 75, 22, 1, 1, 1, 1, 1, 1 ),
       ( 76, 22, 2, 1, 2, 1, 1, 1 ),
       ( 77, 22, 3, 2, 1, 1, 1, 1 ),

       ( 78, 23, 1, 1, 1, 1, 1, 1 ),
       ( 79, 23, 2, 1, 2, 1, 1, 1 ),
       ( 80, 23, 3, 2, 1, 1, 1, 1 ),
       ( 81, 23, 4, 3, 1, 1, 1, 1 ),

       ( 82, 24, 1, 1, 1, 1, 1, 1 ),
       ( 83, 24, 2, 1, 2, 1, 1, 1 ),
       ( 84, 24, 3, 2, 1, 1, 1, 1 ),

       ( 85, 25, 1, 1, 1, 1, 1, 1 ),
       ( 86, 25, 2, 1, 2, 1, 1, 1 ),
       ( 87, 25, 3, 2, 1, 1, 1, 1 ),
       ( 88, 25, 4, 3, 1, 1, 1, 1 ),

       ( 89, 26, 1, 1, 1, 1, 1, 1 ),
       ( 90, 26, 2, 1, 2, 1, 1, 1 ),
       ( 91, 26, 3, 2, 1, 1, 1, 1 ),
       ( 92, 26, 4, 3, 1, 1, 1, 1 ),

       ( 93, 27, 1, 1, 1, 1, 1, 1 ),
       ( 94, 27, 2, 1, 2, 1, 1, 1 ),
       ( 95, 27, 3, 2, 1, 1, 1, 1 ),
       ( 96, 27, 4, 3, 1, 1, 1, 1 ),

       ( 97, 28, 1, 1, 1, 1, 1, 1 ),
       ( 98, 28, 2, 1, 2, 1, 1, 1 ),
       ( 99, 28, 3, 2, 1, 1, 1, 1 ),
       ( 100, 28, 4, 3, 1, 1, 1, 1 ),

       ( 101, 29, 1, 1, 1, 1, 1, 1 ),
       ( 102, 29, 2, 1, 2, 1, 1, 1 ),
       ( 103, 29, 3, 2, 1, 1, 1, 1 ),
       ( 104, 29, 4, 3, 1, 1, 1, 1 ),

       ( 105, 30, 1, 1, 1, 1, 1, 1 ),
       ( 106, 30, 2, 1, 2, 1, 1, 1 ),
       ( 107, 30, 3, 2, 1, 1, 1, 1 ),
       ( 108, 30, 4, 3, 1, 1, 1, 1 )
;