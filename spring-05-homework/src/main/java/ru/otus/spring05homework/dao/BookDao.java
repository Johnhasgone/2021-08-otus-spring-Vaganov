package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Book;

import java.util.List;

public interface BookDao {
    void insert(Book book);

    Book getById(Long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteById(Long id);
}
