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
insert into acl_sid (sid, principal) values
     ('ROLE_ADMIN', false),
     ('ROLE_TEACHER', false),
     ('ROLE_STUDENT', false)
;

insert into acl_class (id, class) values (1, 'ru.otus.spring18homework.domain.Book');

insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values ( 1, 1, NULL, 1, false ),
       ( 1, 2, NULL, 1, false ),
       ( 1, 3, NULL, 1, false ),
       ( 1, 4, NULL, 1, false ),
       ( 1, 5, NULL, 1, false ),
       ( 1, 6, NULL, 1, false ),
       ( 1, 7, NULL, 1, false ),
       ( 1, 8, NULL, 1, false ),
       ( 1, 9, NULL, 1, false ),
       ( 1, 10, NULL, 1, false ),
       ( 1, 11, NULL, 1, false ),
       ( 1, 12, NULL, 1, false ),
       ( 1, 13, NULL, 1, false ),
       ( 1, 14, NULL, 1, false ),
       ( 1, 15, NULL, 1, false ),
       ( 1, 16, NULL, 1, false ),
       ( 1, 17, NULL, 1, false ),
       ( 1, 18, NULL, 1, false ),
       ( 1, 19, NULL, 1, false ),
       ( 1, 20, NULL, 1, false ),
       ( 1, 21, NULL, 1, false ),
       ( 1, 22, NULL, 1, false ),
       ( 1, 23, NULL, 1, false ),
       (  1, 24, NULL, 1, false ),
       ( 1, 25, NULL, 1, false ),
       ( 1, 26, NULL, 1, false ),
       ( 1, 27, NULL, 1, false ),
       ( 1, 28, NULL, 1, false ),
       ( 1, 29, NULL, 1, false ),
       ( 1, 30, NULL, 1, false )
;

insert into ACL_ENTRY (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
values
       ( 1, 1, 1, 1, true, true, true ),
       ( 1, 2, 1, 2, true, true, true ),
       ( 1, 3, 2, 1, true, true, true ),
       ( 1, 4, 3, 1, true, true, true ),
       ( 2, 1, 1, 1, true, true, true ),
       ( 2, 2, 1, 2, true, true, true ),
       ( 2, 3, 2, 1, true, true, true ),

       ( 3, 1, 1, 1, true, true, true ),
       ( 3, 2, 1, 2, true, true, true ),
       ( 3, 3, 2, 1, true, true, true ),
       ( 3, 4, 3, 1, true, true, true ),

       ( 4, 1, 1, 1, true, true, true ),
       ( 4, 2, 1, 2, true, true, true ),
       ( 4, 3, 2, 1, true, true, true ),

       ( 5, 1, 1, 1, true, true, true ),
       ( 5, 2, 1, 2, true, true, true ),
       ( 5, 3, 2, 1, true, true, true ),
       ( 5, 4, 3, 1, true, true, true ),

       ( 6, 1, 1, 1, true, true, true ),
       ( 6, 2, 1, 2, true, true, true ),
       ( 6, 3, 2, 1, true, true, true ),

       ( 7, 1, 1, 1, true, true, true ),
       ( 7, 2, 1, 2, true, true, true ),
       ( 7, 3, 2, 1, true, true, true ),
       ( 7, 4, 3, 1, true, true, true ),

       ( 8, 1, 1, 1, true, true, true ),
       ( 8, 2, 1, 2, true, true, true ),
       ( 8, 3, 2, 1, true, true, true ),

       ( 9, 1, 1, 1, true, true, true ),
       ( 9, 2, 1, 2, true, true, true ),
       ( 9, 3, 2, 1, true, true, true ),
       ( 9, 4, 3, 1, true, true, true ),

       ( 10, 1, 1, 1, true, true, true ),
       ( 10, 2, 1, 2, true, true, true ),
       ( 10, 3, 2, 1, true, true, true ),

       ( 11, 1, 1, 1, true, true, true ),
       ( 11, 2, 1, 2, true, true, true ),
       ( 11, 3, 2, 1, true, true, true ),
       ( 11, 4, 3, 1, true, true, true ),

       ( 12, 1, 1, 1, true, true, true ),
       ( 12, 2, 1, 2, true, true, true ),
       ( 12, 3, 2, 1, true, true, true ),

       ( 13, 1, 1, 1, true, true, true ),
       ( 13, 2, 1, 2, true, true, true ),
       ( 13, 3, 2, 1, true, true, true ),
       ( 13, 4, 3, 1, true, true, true ),

       ( 14, 1, 1, 1, true, true, true ),
       ( 14, 2, 1, 2, true, true, true ),
       ( 14, 3, 2, 1, true, true, true ),

       ( 15, 1, 1, 1, true, true, true ),
       ( 15, 2, 1, 2, true, true, true ),
       ( 15, 3, 2, 1, true, true, true ),
       ( 15, 4, 3, 1, true, true, true ),

       ( 16, 1, 1, 1, true, true, true ),
       ( 16, 2, 1, 2, true, true, true ),
       ( 16, 3, 2, 1, true, true, true ),

       ( 17, 1, 1, 1, true, true, true ),
       ( 17, 2, 1, 2, true, true, true ),
       ( 17, 3, 2, 1, true, true, true ),
       ( 17, 4, 3, 1, true, true, true ),

       ( 18, 1, 1, 1, true, true, true ),
       ( 18, 2, 1, 2, true, true, true ),
       ( 18, 3, 2, 1, true, true, true ),

       ( 19, 1, 1, 1, true, true, true ),
       ( 19, 2, 1, 2, true, true, true ),
       ( 19, 3, 2, 1, true, true, true ),
       ( 19, 4, 3, 1, true, true, true ),

       ( 20, 1, 1, 1, true, true, true ),
       ( 20, 2, 1, 2, true, true, true ),
       ( 20, 3, 2, 1, true, true, true ),

       ( 21, 1, 1, 1, true, true, true ),
       ( 21, 2, 1, 2, true, true, true ),
       ( 21, 3, 2, 1, true, true, true ),
       ( 21, 4, 3, 1, true, true, true ),

       ( 22, 1, 1, 1, true, true, true ),
       ( 22, 2, 1, 2, true, true, true ),
       ( 22, 3, 2, 1, true, true, true ),

       ( 23, 1, 1, 1, true, true, true ),
       ( 23, 2, 1, 2, true, true, true ),
       ( 23, 3, 2, 1, true, true, true ),
       ( 23, 4, 3, 1, true, true, true ),

       ( 24, 1, 1, 1, true, true, true ),
       ( 24, 2, 1, 2, true, true, true ),
       ( 24, 3, 2, 1, true, true, true ),

       ( 25, 1, 1, 1, true, true, true ),
       ( 25, 2, 1, 2, true, true, true ),
       ( 25, 3, 2, 1, true, true, true ),
       ( 25, 4, 3, 1, true, true, true ),

       ( 26, 1, 1, 1, true, true, true ),
       ( 26, 2, 1, 2, true, true, true ),
       ( 26, 3, 2, 1, true, true, true ),
       ( 26, 4, 3, 1, true, true, true ),

       ( 27, 1, 1, 1, true, true, true ),
       ( 27, 2, 1, 2, true, true, true ),
       ( 27, 3, 2, 1, true, true, true ),
       ( 27, 4, 3, 1, true, true, true ),

       ( 28, 1, 1, 1, true, true, true ),
       ( 28, 2, 1, 2, true, true, true ),
       ( 28, 3, 2, 1, true, true, true ),
       (  28, 4, 3, 1, true, true, true ),

       ( 29, 1, 1, 1, true, true, true ),
       ( 29, 2, 1, 2, true, true, true ),
       ( 29, 3, 2, 1, true, true, true ),
       ( 29, 4, 3, 1, true, true, true ),

       ( 30, 1, 1, 1, true, true, true ),
       ( 30, 2, 1, 2, true, true, true ),
       ( 30, 3, 2, 1, true, true, true ),
       ( 30, 4, 3, 1, true, true, true )
;