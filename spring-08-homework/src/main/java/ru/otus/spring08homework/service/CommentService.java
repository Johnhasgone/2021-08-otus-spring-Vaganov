package ru.otus.spring08homework.service;

import ru.otus.spring08homework.dto.BookDto;
import ru.otus.spring08homework.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(Long id);

    List<CommentDto> findByBookId(Long bookId);

    List<CommentDto> findAll();

    CommentDto save(String text, BookDto bookDto);

    void updateTextById(Long id, String text);

    void deleteById(Long id);

    boolean existsById(Long id);
}
