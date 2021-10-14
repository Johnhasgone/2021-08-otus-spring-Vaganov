package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Author;

import java.util.List;

public interface AuthorService {

    Author getById(Long id);

    Author getByName(String name);

    List<Author> getAll();

    Long create(Author author);

    boolean update(Author author);

    boolean deleteById(Long id);
}
