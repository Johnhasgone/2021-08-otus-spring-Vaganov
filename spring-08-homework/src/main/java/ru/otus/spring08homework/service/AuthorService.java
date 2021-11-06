package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> findById(String id);

    Optional<AuthorDto> findByName(String name);

    List<AuthorDto> findAll();

    AuthorDto save(String name);

    void updateNameById(String id, String name);

    void deleteById(String id);

    boolean existsById(String id);
}
