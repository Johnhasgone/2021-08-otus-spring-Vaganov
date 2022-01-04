package ru.otus.spring12homework.service;

import ru.otus.spring12homework.dto.GenreDto;

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