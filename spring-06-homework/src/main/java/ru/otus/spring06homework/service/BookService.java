package ru.otus.spring06homework.service;

import ru.otus.spring06homework.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> findById(Long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(String title, List<String> authorNames, List<String> genreNames);

    boolean updateNameById(Long id, String title);

    boolean deleteById(Long id);
}
