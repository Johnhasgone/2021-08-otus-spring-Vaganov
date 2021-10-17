package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Author;

import java.util.List;

public interface AuthorDao {
    Long insert(Author author);

    boolean update(Author author);

    Author getById(Long id);

    Author getByName(String name);

    List<Author> getAll();

    boolean deleteById(Long id);
}
