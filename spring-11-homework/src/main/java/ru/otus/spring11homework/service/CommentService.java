package ru.otus.spring11homework.service;

import ru.otus.spring11homework.dto.BookDto;
import ru.otus.spring11homework.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(String id);

    List<CommentDto> findByBookId(String bookId);

    List<CommentDto> findAll();

    CommentDto save(String text, BookDto bookDto);

    void updateTextById(String id, String text);

    void deleteById(String id);

    boolean existsById(String id);
}
