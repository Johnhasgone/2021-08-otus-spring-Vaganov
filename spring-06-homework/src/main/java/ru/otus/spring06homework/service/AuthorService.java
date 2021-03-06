package ru.otus.spring06homework.service;

import ru.otus.spring06homework.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> findById(Long id);

    Optional<AuthorDto> findByName(String name);

    List<AuthorDto> findAll();

    AuthorDto save(String name);

    boolean updateNameById(Long id, String name);

    boolean deleteById(Long id);
}
