package ru.otus.spring05homework.dao;

import ru.otus.spring05homework.domain.Genre;

import java.util.List;

public interface GenreDao {
    void insert(Genre genre);

    Genre getById(Long id);

    Genre getByName(String name);

    List<Genre> getAll();

    void deleteById(Long id);
}
