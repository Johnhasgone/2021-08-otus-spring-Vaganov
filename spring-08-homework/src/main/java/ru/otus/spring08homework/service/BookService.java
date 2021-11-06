package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> findById(String id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(String title, List<String> authorNames, List<String> genreNames);

    void updateNameById(String id, String title);

    void deleteById(String id);

    boolean existsById(String id);
}
