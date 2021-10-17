package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Book;

import java.util.List;

public interface BookDao {
    Long insert(Book book);

    boolean update(Book book);

    Book getById(Long id);

    Book getByTitle(String title);

    List<Book> getAll();

    boolean deleteById(Long id);
}
