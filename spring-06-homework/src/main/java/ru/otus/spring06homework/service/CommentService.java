package ru.otus.spring06homework.service;

import ru.otus.spring06homework.dto.BookDto;
import ru.otus.spring06homework.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(Long id);

    List<CommentDto> findByBook(BookDto bookDto);

    List<CommentDto> findAll();

    CommentDto save(String text, BookDto bookDto);

    boolean updateTextById(Long id, String text);

    boolean deleteById(Long id);
}
