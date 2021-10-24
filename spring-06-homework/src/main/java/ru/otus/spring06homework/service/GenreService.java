package ru.otus.spring06homework.service;

import ru.otus.spring06homework.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDto> findById(Long id);

    Optional<GenreDto> findByName(String name);

    List<GenreDto> findAll();

    GenreDto save(String name);

    boolean updateNameById(Long id, String name);

    boolean deleteById(Long id);
}
