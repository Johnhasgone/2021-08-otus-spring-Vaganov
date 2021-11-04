package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> findById(Long id);

    Optional<AuthorDto> findByName(String name);

    List<AuthorDto> findAll();

    AuthorDto save(String name);

    void updateNameById(Long id, String name);

    void deleteById(Long id);

    boolean existsById(Long id);
}
