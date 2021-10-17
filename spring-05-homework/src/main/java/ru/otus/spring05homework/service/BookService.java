package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Book;

import java.util.List;

public interface BookService {

    Book getById(Long id);

    Book getByName(String name);

    List<Book> getAll();

    Long create(Book book);

    boolean update(Book book);

    boolean deleteById(Long id);
}
