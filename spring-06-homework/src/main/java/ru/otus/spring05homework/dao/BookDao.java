package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    List<Book> findByTitle(String name);
    int update(Book book);
    int deleteById(Long id);
}
