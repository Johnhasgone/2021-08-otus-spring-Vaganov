package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre getById(Long id);

    Genre getByName(String name);

    List<Genre> getAll();

    Long create(Genre genre);

    void update(Genre genre);

    void deleteById(Long id);
}
