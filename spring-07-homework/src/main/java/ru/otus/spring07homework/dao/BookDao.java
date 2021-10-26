package ru.otus.spring07homework.dao;

import ru.otus.spring07homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    List<Book> findByTitle(String name);
    int updateNameById(Long id, String title);
    int deleteById(Long id);
}
