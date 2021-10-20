package ru.otus.spring06homework.service;

import ru.otus.spring06homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    List<Book> findAll();

    Book save(Book book);

    boolean update(Book book);

    boolean deleteById(Long id);
}
