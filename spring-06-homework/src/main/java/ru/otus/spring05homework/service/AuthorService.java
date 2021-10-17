package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(Long id);

    List<Author> findByName(String name);

    List<Author> findAll();

    Author save(Author author);

    boolean updateNameById(Long id, String name);

    boolean deleteById(Long id);
}
