insert into author(id, name) values(1, 'Афанасий Афанасьевич Фет');
insert into author(id, name) values(2, 'Сергей Михалков');

insert into genre(id, name) values(1, 'проза');
insert into genre(id, name) values(2, 'поэзия');

insert into book(id, name, author_id, genre_id) values (1,'Стихотворения', 1, 2);
insert into book(id, name, author_id, genre_id) values (2,'Сборник рассказов', 2, 1);