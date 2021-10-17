package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Genre;

import java.util.List;

public interface GenreDao {
    Long insert(Genre genre);

    boolean update(Genre genre);

    Genre getById(Long id);

    Genre getByName(String name);

    List<Genre> getAll();

    boolean deleteById(Long id);
}
