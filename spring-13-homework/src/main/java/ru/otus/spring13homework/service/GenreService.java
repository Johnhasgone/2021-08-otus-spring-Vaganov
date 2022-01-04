package ru.otus.spring13homework.service;

import ru.otus.spring13homework.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDto> findById(Long id);

    Optional<GenreDto> findByName(String name);

    List<GenreDto> findAll();

    GenreDto save(String name);

    void deleteById(Long id);

    boolean existsById(Long id);
}
