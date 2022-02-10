package ru.otus.spring17homework.service;

import ru.otus.spring17homework.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(Long id);

    List<CommentDto> findByBookId(Long bookId);

    List<CommentDto> findAll();

    CommentDto save(Long bookId, String text);

    void deleteById(Long id);

    boolean existsById(Long id);
}
