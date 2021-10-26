package ru.otus.spring07homework.dao;

import ru.otus.spring07homework.domain.Book;
import ru.otus.spring07homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findAll();
    List<Comment> findByBook(Book book);
    int updateTextById(Long id, String text);
    int deleteById(Long id);
}
