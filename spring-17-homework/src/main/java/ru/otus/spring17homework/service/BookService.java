package ru.otus.spring17homework.service;

import ru.otus.spring17homework.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findAll();

    BookDto save(BookDto bookDto);

    void deleteById(Long id);

    boolean existsById(Long id);
}
