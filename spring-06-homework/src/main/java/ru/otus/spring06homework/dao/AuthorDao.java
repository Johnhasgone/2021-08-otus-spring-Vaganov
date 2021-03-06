package ru.otus.spring06homework.dao;

import ru.otus.spring06homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author save(Author author);
    Optional<Author> findById(Long id);
    List<Author> findAll();
    List<Author> findByName(String name);
    int updateNameById(Long id, String name);
    int deleteById(Long id);
}
