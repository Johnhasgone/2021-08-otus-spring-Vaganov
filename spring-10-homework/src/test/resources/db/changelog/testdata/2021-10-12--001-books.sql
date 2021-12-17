insert into author(id, name) values (1, 'Афанасий Афанасьевич Фет');
insert into author(id, name) values (2, 'Сергей Михалков');
insert into author(id, name) values (3, 'Алексей Толстой');
insert into author(id, name) values (4, 'Аркадий Стругацкий');
insert into author(id, name) values (5, 'Борис Стругацкий');

insert into genre(id, name) values (1, 'проза');
insert into genre(id, name) values (2, 'роман');
insert into genre(id, name) values (3, 'поэзия');
insert into genre(id, name) values (4, 'комедия');

insert into book(id, title) values (1,'Стихотворения');
insert into book(id, title) values (2,'Сборник рассказов');
insert into book(id, title) values (3,'Улитка на склоне');

insert into book_author(book_id, author_id) values
                                                    (1, 1),
                                                    (2, 2),
                                                    (3, 4),
                                                    (3, 5)
;

insert into book_genre(book_id, genre_id) values
                                                    (1, 3),
                                                    (2, 1),
                                                    (3, 1),
                                                    (3, 2)
;

insert into comment(id, text, book_id) values
                                              (1, 'Отличная книга', 1),
                                              (2, 'Превосходно', 1),
                                              (3, 'Так себе', 1),
                                              (4, 'Понравилось', 2),
                                              (5, 'Увлекательно', 2),
                                              (6, 'Захватывает с первых страниц', 3),
                                              (7, 'Какая-то муть', 3)

;