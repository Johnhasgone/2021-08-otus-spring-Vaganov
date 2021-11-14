package ru.otus.spring09homework.service;

import ru.otus.spring09homework.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDto> findById(Long id);

    Optional<GenreDto> findByName(String name);

    List<GenreDto> findAll();

    GenreDto save(String name);

    void updateNameById(Long id, String name);

    void deleteById(Long id);

    boolean existsById(Long id);
}
