package ru.otus.spring09homework.service;

import ru.otus.spring09homework.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> findById(Long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(BookDto bookDto);

    void deleteById(Long id);

    boolean existsById(Long id);
}
