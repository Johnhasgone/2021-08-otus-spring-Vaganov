package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDto> findById(String id);

    Optional<GenreDto> findByName(String name);

    List<GenreDto> findAll();

    GenreDto save(String name);

    void updateNameById(String id, String name);

    void deleteById(String id);

    boolean existsById(String id);
}
