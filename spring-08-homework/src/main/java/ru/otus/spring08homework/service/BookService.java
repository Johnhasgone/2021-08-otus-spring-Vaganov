package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> findById(Long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(String title, List<String> authorNames, List<String> genreNames);

    void updateNameById(Long id, String title);

    void deleteById(Long id);

    boolean existsById(Long id);
}
