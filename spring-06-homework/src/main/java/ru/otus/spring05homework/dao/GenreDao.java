package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre save(Genre genre);

    int updateNameById(Long id, String name);

    Optional<Genre> findById(Long id);

    List<Genre> findByName(String name);

    List<Genre> findAll();

    int deleteById(Long id);
}
