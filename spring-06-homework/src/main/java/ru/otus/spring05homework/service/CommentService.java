package ru.otus.spring05homework.service;

import ru.otus.spring05homework.domain.Book;
import ru.otus.spring05homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    List<Comment> findByBook(Book book);

    List<Comment> findAll();

    Comment save(Comment comment);

    boolean updateTextById(Long id, String text);

    boolean deleteById(Long id);
}
