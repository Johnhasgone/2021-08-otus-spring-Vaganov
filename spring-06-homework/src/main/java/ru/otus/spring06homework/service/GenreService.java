package ru.otus.spring06homework.service;

import ru.otus.spring06homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    Genre save(Genre author);

    boolean updateNameById(Long id, String name);

    boolean deleteById(Long id);
}
