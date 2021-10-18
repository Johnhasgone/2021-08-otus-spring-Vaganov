package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> findById(Long id);

    List<Genre> findByName(String name);

    List<Genre> findAll();

    Genre save(Genre author);

    boolean updateNameById(Long id, String name);

    boolean deleteById(Long id);
}
