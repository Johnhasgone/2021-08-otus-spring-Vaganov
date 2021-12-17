package ru.otus.spring10homework.service;

import ru.otus.spring10homework.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(BookDto bookDto);

    void deleteById(Long id);

    boolean existsById(Long id);
}
