package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Book;

import java.util.List;

public interface BookDao {
    Long insert(Book book);

    void update(Book book);

    Book getById(Long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteById(Long id);
}